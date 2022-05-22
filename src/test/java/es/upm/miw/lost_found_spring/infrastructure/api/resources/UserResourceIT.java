package es.upm.miw.lost_found_spring.infrastructure.api.resources;

import es.upm.miw.lost_found_spring.domain.model.User;
import es.upm.miw.lost_found_spring.infrastructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static es.upm.miw.lost_found_spring.infrastructure.api.resources.UserResource.*;
import static org.junit.jupiter.api.Assertions.*;

@RestTestConfig
public class UserResourceIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testLogin() {
        this.restClientTestService.loginAdmin(this.webTestClient);
        assertTrue(this.restClientTestService.getToken().length() > 10);
    }

    @Test
    void testCreate() {
        User user = User.builder().userName("karim").email("karim@karim.karim")
                .familyName("karim").mobile(123456).location("Madrid").password("123").build();

        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(USERS)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(Assertions::assertNotNull)
                .value(user1 -> {
                    assertEquals("karim", user1.getUserName());
                    assertEquals(123456, user1.getMobile());
                    assertNotNull(user1.getRegistrationDate());
                });
    }


    @Test
    void testReadByEmailAndUpdate() {

        User user = this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(USERS + EMAIL, "karim@a.a")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(Assertions::assertNotNull)
                .value(user2 -> {
                    assertEquals("abc", user2.getUserName());
                    assertEquals("Name1", user2.getFamilyName());
                    assertEquals("Madrid", user2.getLocation());

                })
                .returnResult()
                .getResponseBody();
        assertNotNull(user);
        user.setLocation("Spain");
        user = this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(USERS + EMAIL, "karim@a.a")
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(Assertions::assertNotNull)
                .value(returnUser -> assertEquals("Spain", returnUser.getLocation()))
                .returnResult()
                .getResponseBody();


    }

    @Test
    void testFindByUserNameAndEmailAndMobileAndLocalisationNullSafe() {

        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(USERS + SEARCH)
                        .queryParam("location", "Madrid")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .value(Assertions::assertNotNull)
                .value(users ->
                        assertTrue(users.stream().allMatch(user -> user.getFamilyName().contains("karim"))));

    }

    @Test
    void testDeleteUser() {
        User user = User.builder().userName("karim").email("karim@k.k")
                .familyName("karim").mobile(123456).location("Madrid").password("123").build();

        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(USERS)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isOk();

        this.restClientTestService.loginAdmin(webTestClient)
                .delete()
                .uri(USERS + EMAIL, "karim@k.k")
                .exchange()
                .expectStatus().isOk();

    }
}
