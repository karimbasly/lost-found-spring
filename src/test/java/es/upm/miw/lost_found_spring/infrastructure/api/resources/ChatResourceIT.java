package es.upm.miw.lost_found_spring.infrastructure.api.resources;

import es.upm.miw.lost_found_spring.domain.model.Chat;
import es.upm.miw.lost_found_spring.domain.model.Message;
import es.upm.miw.lost_found_spring.infrastructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static es.upm.miw.lost_found_spring.infrastructure.api.resources.ChatResource.CHAT;
import static es.upm.miw.lost_found_spring.infrastructure.api.resources.ChatResource.SEND_EMAIL_FROM;
import static org.junit.jupiter.api.Assertions.*;

@RestTestConfig
public class ChatResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testCreate() {
        Message message = Message.builder().text("aaa").senderEmail("karim@a.a").build();
        Chat chat = Chat.builder().sendEmailFrom("karim@a.a").sendEmailTo("karim@a.a").lastMessage("aaa").userNamesFrom("abc").userNamesTo("abc").message(List.of(message)).build();

        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(CHAT)
                .body(Mono.just(chat), Chat.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Chat.class)
                .value(Assertions::assertNotNull)
                .value(chat1 -> {
                    assertEquals("karim@a.a", chat1.getSendEmailFrom());
                    assertEquals("karim@a.a", chat1.getSendEmailTo());
                    assertEquals("aaa", chat1.getLastMessage());
                    assertNotNull(chat1.getMessage());
                    assertTrue(chat1.getMessage().stream().anyMatch(announcement2 -> announcement2.getText().contains("aaa")));
                    assertNotNull(chat1.getMessage().stream().map(Message::getId));
                    //Equals("abc", chat1.getUserName());
                });
    }

    @Test
    void testFindByuserFrom() {

        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(CHAT + SEND_EMAIL_FROM, "karim1", "karim1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Chat.class)
                .value(Assertions::assertNotNull)
                .value(chat1 -> {
                    assertTrue(chat1.stream().anyMatch(chat -> chat.getLastMessage().contains("ok")));
                    assertTrue(chat1.stream().anyMatch(chat -> chat.getLastMessage().contains("ok1")));

                    //assertEquals("OK", chat1.getUserNamesFrom());
                });
    }
}
