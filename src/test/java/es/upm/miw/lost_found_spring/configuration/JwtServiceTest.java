package es.upm.miw.lost_found_spring.configuration;

import es.upm.miw.lost_found_spring.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void testJwtExceptionNotBearer() {
        assertTrue(jwtService.userName("Not Bearer").isEmpty());
    }

    @Test
    void testJwtUtilExtract() {
        assertEquals("t.t.t", jwtService.extractToken("Bearer t.t.t"));
    }

    @Test
    void testCreateTokenAndVerify() {
        //String token = jwtService.createToken("$$$$$$$", "adm", Role.ADMIN.name());
        String token = jwtService.createToken("user-id", "name", "ROLE");
        assertEquals(3, token.split("\\.").length);
        assertTrue(token.length() > 30);
        assertEquals("user-id", jwtService.email(token));
        assertEquals("name", jwtService.userName(token));
        assertEquals("ROLE", jwtService.role(token));
    }
}
