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
    public static final String SEND_EMAIL_FROM = "/{sendEmailFrom}";
    public static final String SEND_EMAIL_TO = "/{sendEmailTo}";
    public static final String ID_ID = "/{id}";
    private final ChatService chatService;

    @Autowired
    public ChatResource(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<Chat> createChat(@RequestBody Chat chat) {
        return this.chatService.createChat(chat);

    }

    @GetMapping(SEND_EMAIL_FROM + SEND_EMAIL_TO)
    public Flux<Chat> getChatBySendEmailFrom(@PathVariable String sendEmailFrom, @PathVariable String sendEmailTo) {
        return this.chatService.findBySendEmailFrom(sendEmailFrom, sendEmailTo);
    }

    @GetMapping(ID_ID)
    public Mono<Chat> getChatByID(@PathVariable String id) {
        return this.chatService.findById(id);
        //.map(ChatDto::ofValue);
    }

    @PutMapping(ID_ID)
    public Mono<Chat> addMessage(@PathVariable String id, @RequestBody Chat chat) {
        return this.chatService.addMessage(id, chat);
    }
}
