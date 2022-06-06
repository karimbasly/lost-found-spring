package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos;

import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.AnnouncementEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface AnnouncementReactive extends ReactiveSortingRepository<AnnouncementEntity, String> {

    @Query("{$and:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { category : {$regex:[0], $options: 'i'} } },"
            + "?#{ [1] == null ? {_id : {$ne:null}} : { type : {$regex:[1], $options: 'i'} } },"
            + "?#{ [2] == null ? {_id : {$ne:null}} : { location : {$regex:[2], $options: 'i'} } },"
            + "] }")
    Flux<AnnouncementEntity> findByTypeAndCategoryLocalisationNullSafe(String category, String type, String location);

    //Flux<AnnouncementEntity>findByUserEmail(Mono<UserEntity> userEntityMono);
  /* @Query("{$and:[" // allow NULL in barcode
            + "?#{ {_id : {$ne:null}} : { userEntity : { $elemMatch: { email: { $regex:[0], $options: 'i'} } } },"
            + "] }")*/
    Flux<AnnouncementEntity> findByUserEmail(String userEmail);


}
