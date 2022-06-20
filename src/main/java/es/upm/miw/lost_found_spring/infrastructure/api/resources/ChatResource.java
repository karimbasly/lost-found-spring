package es.upm.miw.lost_found_spring.infrastructure.api.resources;

import es.upm.miw.lost_found_spring.domain.model.Chat;
import es.upm.miw.lost_found_spring.domain.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ChatResource.CHAT)
public class ChatResource {
    public static final String CHAT = "/chats";
    public static final String SEND_EMAIL_FROM = "/{sendEmailFrom}/{sendEmailTo}";

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

    @GetMapping(SEND_EMAIL_FROM)
    public Flux<Chat> getChayBySendEmailFrom(@PathVariable String sendEmailFrom, @PathVariable String sendEmailTo) {
        return this.chatService.findBySendEmailFrom(sendEmailFrom, sendEmailTo);
    }
}
