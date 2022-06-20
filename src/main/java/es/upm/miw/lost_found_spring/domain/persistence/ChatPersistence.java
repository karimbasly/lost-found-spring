package es.upm.miw.lost_found_spring.domain.persistence;

import es.upm.miw.lost_found_spring.domain.model.Chat;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ChatPersistence {
    Mono<Chat> createChat(Chat chat);

    Flux<Chat> findBySendEmailFrom(String sendEmailFrom, String sendEmailTo);
}
