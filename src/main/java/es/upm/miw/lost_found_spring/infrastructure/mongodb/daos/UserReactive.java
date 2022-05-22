package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos;

import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.UserEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface UserReactive extends ReactiveSortingRepository<UserEntity, String> {
    //Mono<UserEntity> findByUserName(String userName);


    Mono<UserEntity> findByEmail(String email);

    @Query("{$and:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { userName : {$regex:[0], $options: 'i'} } },"
            + "?#{ [1] == null ? {_id : {$ne:null}} : { email : {$regex:[1], $options: 'i'} } },"
            + "?#{ [2] == null ? {_id : {$ne:null}} : { mobile : {$gte:[2]} } },"
            + "?#{ [3] == null ? {_id : {$ne:null}} : { location : {$regex:[3], $options: 'i'} } },"
            + "] }")
    Flux<UserEntity> findByNameAndEmailAndLocalisationNullSafe(String userName, String email, Integer mobile, String location);

    Mono<Void> deleteByEmail(String email);

}
