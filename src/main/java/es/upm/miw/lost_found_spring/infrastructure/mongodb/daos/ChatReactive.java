package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos;

import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.ChatEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface ChatReactive extends ReactiveSortingRepository<ChatEntity, String> {
    /*@Query("{$or:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { sendEmailFrom : {$regex:[0], $options: 'i'} } },"
            + "?#{ [1] == null ? {_id : {$ne:null}} : { sendEmailTo : {$regex:[1], $options: 'i'} } },"

            + "] }")*


     */
    Flux<ChatEntity> findBySendEmailFromOrSendEmailTo(String sendEmailFrom, String sendEmailTo);
}
