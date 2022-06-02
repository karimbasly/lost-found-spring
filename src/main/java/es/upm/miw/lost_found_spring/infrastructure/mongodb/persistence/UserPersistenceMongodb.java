package es.upm.miw.lost_found_spring.infrastructure.mongodb.persistence;

import es.upm.miw.lost_found_spring.domain.Persistence.UserPersistence;
import es.upm.miw.lost_found_spring.domain.exceptions.ConflictException;
import es.upm.miw.lost_found_spring.domain.exceptions.NotFoundException;
import es.upm.miw.lost_found_spring.domain.model.User;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.UserReactive;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserPersistenceMongodb implements UserPersistence {

    private final UserReactive userReactive;

    @Autowired
    public UserPersistenceMongodb(UserReactive userReactive) {
        this.userReactive = userReactive;
    }
    @Override
    public Mono<User> findByEmail(String email) {
        return this.userReactive.findByEmail(email)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent user: " + email)))
                .map(UserEntity::toUser);
    }

    @Override
    public Mono<User> create(User user) {
        UserEntity userEntity = new UserEntity();
        user.setPassword((new BCryptPasswordEncoder().encode(user.getPassword())));
        BeanUtils.copyProperties(user, userEntity);
        return this.assertEmailNotExist(user.getEmail())
                .then(this.userReactive.save(userEntity))
                .map(UserEntity::toUser);
    }

    @Override
    public Mono<User> update(String email, User user) {
        Mono<UserEntity> userEntityMono;
        if (!email.equals(user.getEmail())) {
            userEntityMono = this.assertEmailNotExist(user.getEmail())
                    .then(this.userReactive.findByEmail(email));
        } else {
            userEntityMono = this.userReactive.findByEmail(email);
        }
        return userEntityMono
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent user email: " + email)))
                .map(userEntity -> {
                    BeanUtils.copyProperties(user, userEntity);
                    return userEntity;
                })
                .flatMap(this.userReactive::save)
                .map(UserEntity::toUser);
    }

    @Override
    public Flux<User> findByNameAndEmailAndLocalisationNullSafe(String userName, String email, Integer mobile, String location) {
        return this.userReactive.findByNameAndEmailAndLocalisationNullSafe(userName, email, mobile, location)
                .map(UserEntity::toUser);
    }

    @Override
    public Mono<Void> delete(String email) {
        return this.userReactive.findByEmail(email)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent email: " + email)))
                .then(this.userReactive.deleteByEmail(email));
    }

    private Mono<Void> assertEmailNotExist(String email) {
        return this.userReactive.findByEmail(email)
                .flatMap(userEntity -> Mono.error(
                        new ConflictException("user Email already exists : " + email)
                ));
    }
}
