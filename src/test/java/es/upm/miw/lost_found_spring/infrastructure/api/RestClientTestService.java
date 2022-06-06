package es.upm.miw.lost_found_spring.infrastructure.api;


import es.upm.miw.lost_found_spring.configuration.JwtService;
import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;

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
            this.token = jwtService.createToken(user, name, role.name());

        }
        return webTestClient.mutate()
                .defaultHeader("Authorization", "Bearer " + this.token).build();
    }

    public WebTestClient loginAdmin(WebTestClient webTestClient) {
        return this.login(Role.ADMIN, "6", "adm", webTestClient);
    }

    public void logout() {
        this.token = null;
    }

    public String getToken() {
        return token;
    }

}
