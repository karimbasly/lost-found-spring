package es.upm.miw.lost_found_spring.domain.services;

import es.upm.miw.lost_found_spring.TestConfig;
import es.upm.miw.lost_found_spring.domain.exceptions.NotFoundException;
import es.upm.miw.lost_found_spring.domain.model.Announcement;
import es.upm.miw.lost_found_spring.domain.model.Category;
import es.upm.miw.lost_found_spring.domain.model.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
public class AnnouncementServicesIT {
    @Autowired
    private AnnouncementService announcementService;

    @Test
    void testCreate() {

        StepVerifier
                .create(this.announcementService.createAnnouncement(Announcement.builder().id("id1").category(Category.PETS).description("des")
                        .name("Cat").type(Type.LOST).userPhoto("").userEmail("karim@a.a").userName("abc")
                        .build()))
                .expectNextMatches(announcement -> {
                    assertEquals("Cat", announcement.getName());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testReadByIdNotFound() {
        StepVerifier
                .create(this.announcementService.findById("id8"))
                .expectError(NotFoundException.class);
    }
}
