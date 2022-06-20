package es.upm.miw.lost_found_spring.domain.services;

import es.upm.miw.lost_found_spring.domain.model.Chat;
import es.upm.miw.lost_found_spring.domain.persistence.ChatPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ChatService {
    private final ChatPersistence chatPersistence;

    @Autowired
    public ChatService(ChatPersistence chatPersistence) {
        this.chatPersistence = chatPersistence;
    }

    public Mono<Chat> createChat(Chat chat) {
        return this.chatPersistence.createChat(chat);
    }

    public Flux<Chat> findBySendEmailFrom(String sendEmailFrom, String sendEmailTo) {
        return this.chatPersistence.findBySendEmailFrom(sendEmailFrom, sendEmailFrom);
    }
}
