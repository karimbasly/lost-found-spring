package es.upm.miw.lost_found_spring.infrastructure.api.resources;

import es.upm.miw.lost_found_spring.domain.model.User;
import es.upm.miw.lost_found_spring.infrastructure.api.RestClientTestService;
import es.upm.miw.lost_found_spring.infrastructure.api.dtos.UserDto;
import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        User user = User.builder().userName("karim").email("karim@karim.karim").role(Role.CUSTOMER)
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
                .expectBodyList(UserDto.class)
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

    @Test
    void testReadUserNotFound() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .get().uri(USERS + EMAIL, "ada@aa.aa")
                .exchange().expectStatus().isNotFound();
    }

    @Test
    void testDeleteUserForbidden() {
        this.restClientTestService.loginCustomer(this.webTestClient)
                .delete()
                .uri(USERS + EMAIL, "karim@k.k")
                .exchange().expectStatus().isUnauthorized();
    }

    @Test
    void testCreateUserConflict() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(User.builder().userName("abc").email("karim@a.a").familyName("Name1")
                        .mobile(12365).location("Madrid")
                        .password("a").build()), User.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testCreateUserBadNumber() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(UserDto.builder().userName("a").userName("kk").build()), UserDto.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

}