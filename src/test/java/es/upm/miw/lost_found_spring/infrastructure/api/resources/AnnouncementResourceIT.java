package es.upm.miw.lost_found_spring.infrastructure.api.resources;

import es.upm.miw.lost_found_spring.domain.model.Announcement;
import es.upm.miw.lost_found_spring.domain.model.Category;
import es.upm.miw.lost_found_spring.domain.model.Type;
import es.upm.miw.lost_found_spring.infrastructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static es.upm.miw.lost_found_spring.infrastructure.api.resources.AnnouncementResource.*;
import static org.junit.jupiter.api.Assertions.*;

@RestTestConfig
public class AnnouncementResourceIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testCreateAndFindByEMAIL() {
        Announcement announcement = Announcement.builder().category(Category.WALLET).type(Type.FOUND).photo("")
                .description("test").id("id6").location("Sousse").userEmail("karim@a.a").name("wallet")
                .build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(ANNOUNCEMENT)
                .body(Mono.just(announcement), Announcement.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Announcement.class)
                .value(Assertions::assertNotNull)
                .value(announcement1 -> {
                    assertEquals("test", announcement1.getDescription());
                    assertEquals("Sousse", announcement1.getLocation());
                    assertEquals(0.0, announcement1.getLat());
                    assertEquals("abc", announcement1.getUserName());
                });
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(ANNOUNCEMENT + USERS + USER_EMAIL, "karim@a.a")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Announcement.class)
                .value(announcementEntity ->
                        assertTrue(announcementEntity.stream().anyMatch(announcement2 -> announcement2.getLocation().contains("tunis"))));

    }

    @Test
    void testFindByIdNotFoundException() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(ANNOUNCEMENT + ID_ID, "id20025")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testFindEmailIdNotFoundException() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(ANNOUNCEMENT + USER_EMAIL, "ka@k.k")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testFindByIdAndUpdate() {
        Announcement announcement = this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(ANNOUNCEMENT + ID_ID, "id1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Announcement.class)
                .value(Assertions::assertNotNull)
                .value(announcement1 -> {
                    assertEquals("Cat", announcement1.getName());
                    assertEquals(Category.PETS, announcement1.getCategory());
                    assertEquals("tunis", announcement1.getLocation());
                    assertEquals("karim@a.a", announcement1.getUserEmail());
                })
                .returnResult()
                .getResponseBody();
        assertNotNull(announcement);

        announcement.setDescription("desUpdate");
        announcement = this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(ANNOUNCEMENT + ID_ID, "id1")
                .body(Mono.just(announcement), Announcement.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Announcement.class)
                .value(Assertions::assertNotNull)
                .value(returnAnnouncement -> assertEquals("desUpdate", returnAnnouncement.getDescription()))
                .returnResult()
                .getResponseBody();


    }


    @Test
    void testFindByTypeAndCategoryLocalisationNullSafe() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ANNOUNCEMENT + SEARCH)
                        .queryParam("location", "tunis")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Announcement.class)
                .value(Assertions::assertNotNull)
                .value(announcements ->
                        assertTrue(announcements.stream().allMatch(announcement -> announcement.getName().contains("Cat"))));


    }
}
