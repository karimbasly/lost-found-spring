package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos;

import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.UserEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import reactor.core.publisher.Mono;



public interface UserReactive extends ReactiveSortingRepository<UserEntity, String> {
    //Mono<UserEntity> findByUserName(String userName);


    Mono<UserEntity> findByEmail(String email);

    Mono<UserEntity> findByUserName(String userName);
}
