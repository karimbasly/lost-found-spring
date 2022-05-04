package es.upm.miw.lost_found_spring.domain.Persistence;

import es.upm.miw.lost_found_spring.domain.model.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserPersistence {

    //Mono<User> readByUserName(String userName);

    Mono<User> findByEmail(String email);

    Mono<User> create(User user);
}
