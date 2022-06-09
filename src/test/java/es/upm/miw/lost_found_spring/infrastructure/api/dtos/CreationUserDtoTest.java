package es.upm.miw.lost_found_spring.infrastructure.api.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreationUserDtoTest {
    @Test
    void testOfNameEmailMobile() {
        UserDto user = UserDto.builder().email("k@k.k")
                .userName("karim")
                .familyName("bsl")
                .mobile(1244545)
                .location("madrid")
                .build();
        assertEquals("madrid", user.getLocation());
    }
}
