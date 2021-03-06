package es.upm.miw.lost_found_spring.infrastructure.api;


import es.upm.miw.lost_found_spring.configuration.JwtService;
import es.upm.miw.lost_found_spring.infrastructure.api.dtos.TokenDto;
import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import es.upm.miw.lost_found_spring.infrastructure.api.resources.UserResource;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Service
public class RestClientTestService {
    private final JwtService jwtService;
    private String token;

    @Autowired
    public RestClientTestService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private boolean isRole(Role role) {
        return this.token != null && jwtService.role(this.token).equals(role.name());
    }

    private WebTestClient login(Role role, String user, String name, WebTestClient webTestClient) {
        if (!this.isRole(role)) {
            // this.token = jwtService.createToken(user, name, role.name());
            return login(name, user, webTestClient);

        }
        return webTestClient.mutate()
                .defaultHeader("Authorization", "Bearer " + this.token).build();
    }

    public WebTestClient login(String user, String name, WebTestClient webTestClient) {
        TokenDto tokenDto = webTestClient
                .mutate().filter(basicAuthentication(name, "admin")).build()
                .post().uri(UserResource.USERS + UserResource.TOKEN)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenDto.class)
                .value(Assertions::assertNotNull)
                .returnResult().getResponseBody();
        if (tokenDto != null) {
            this.token = tokenDto.getToken();
        }
        return webTestClient.mutate()
                .defaultHeader("Authorization", "Bearer " + this.token).build();
    }


    public WebTestClient loginAdmin(WebTestClient webTestClient) {
        return this.login(Role.ADMIN, "admin@admin.admin", "adm", webTestClient);
    }

    public WebTestClient loginCustomer(WebTestClient webTestClient) {
        return this.login(Role.CUSTOMER, "test@test.test", "test", webTestClient);
    }

    public void logout() {
        this.token = null;
    }

    public String getToken() {
        return token;
    }

}
