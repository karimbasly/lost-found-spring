package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous;

import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.ChatEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatDao extends MongoRepository<ChatEntity, String> {
}
