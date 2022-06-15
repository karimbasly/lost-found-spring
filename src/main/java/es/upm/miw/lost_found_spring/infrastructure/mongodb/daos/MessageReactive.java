package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos;

import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.MessageEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface MessageReactive extends ReactiveSortingRepository<MessageEntity, String> {
}
