package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos;

import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.ChatEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ChatReactive extends ReactiveSortingRepository<ChatEntity, String> {
}
