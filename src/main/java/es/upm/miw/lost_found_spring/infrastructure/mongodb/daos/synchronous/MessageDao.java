package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous;

import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageDao extends MongoRepository<MessageEntity, String> {
}
