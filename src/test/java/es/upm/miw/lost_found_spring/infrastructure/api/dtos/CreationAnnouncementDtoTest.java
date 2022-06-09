package es.upm.miw.lost_found_spring.infrastructure.api.dtos;

import es.upm.miw.lost_found_spring.domain.model.Category;
import es.upm.miw.lost_found_spring.domain.model.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreationAnnouncementDtoTest {

    @Test
    void testAnnouncementDtoOfvalue() {
        AnnouncementDto announcementDto = AnnouncementDto.builder()
                .id("123")
                .name("cat")
                .description("lost cat")
                .category(Category.PETS)
                .type(Type.LOST)
                .photo("")
                .location("")
                .userEmail("krim@k.k")
                .username("karim")
                .userPhoto("")
                .build();
        assertNotNull(announcementDto);
        assertEquals("123", announcementDto.getId());
    }

}
