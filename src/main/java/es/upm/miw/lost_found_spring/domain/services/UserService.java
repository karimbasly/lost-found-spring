package es.upm.miw.lost_found_spring.domain.services;


import es.upm.miw.lost_found_spring.configuration.JwtService;
import es.upm.miw.lost_found_spring.domain.Persistence.UserPersistence;
import es.upm.miw.lost_found_spring.domain.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
public class UserService {


    private UserPersistence userPersistence;
    private JwtService jwtService;

    @Autowired
    public UserService(UserPersistence userPersistence, JwtService jwtService) {
        this.userPersistence = userPersistence;
        this.jwtService = jwtService;
    }

    public Mono<String> login(String userName) {
        return this.userPersistence.findByEmail(userName)
                .map(user -> jwtService.createToken(user.getEmail(), user.getUserName(), user.getRole().name()));
    }

    public Mono<User> createUser(User user) {
        user.setRegistrationDate(LocalDateTime.now());
        return this.userPersistence.create(user);
    }

    public Mono<User> readByEmail(String email) {
        return this.userPersistence.findByEmail(email);
    }

    public Mono<User> update(String email, User user) {
        return this.userPersistence.findByEmail(email)
                .map(dataArticle -> {
                    BeanUtils.copyProperties(user, dataArticle, "registrationDate");
                    return dataArticle;
                }).flatMap(dataArticle -> this.userPersistence.update(email, dataArticle));
    }
}

