package es.upm.miw.lost_found_spring.domain.Persistence;

import es.upm.miw.lost_found_spring.domain.model.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserPersistence {


    Mono<User> findByEmail(String email);

    Mono<User> create(User user);

    Mono<User> update(String email, User dataArticle);

    Flux<User> findByNameAndEmailAndLocalisationNullSafe(String userName, String email, Integer mobile, String location);

    Mono<Void> delete(String email);
}
