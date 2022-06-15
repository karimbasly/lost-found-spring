package es.upm.miw.lost_found_spring.infrastructure.api.resources;

import es.upm.miw.lost_found_spring.domain.model.Chat;
import es.upm.miw.lost_found_spring.domain.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ChatResource.CHAT)
public class ChatResource {
    public static final String CHAT = "/chats";

    private final ChatService chatService;

    @Autowired
    public ChatResource(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<Chat> createChat(@RequestBody Chat chat) {
        //chat.doDefault();
        return this.chatService.createChat(chat);

    }
}
