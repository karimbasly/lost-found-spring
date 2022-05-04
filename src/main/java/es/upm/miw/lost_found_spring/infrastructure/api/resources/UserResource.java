package es.upm.miw.lost_found_spring.infrastructure.api.resources;

import es.upm.miw.lost_found_spring.domain.services.UserService;

import es.upm.miw.lost_found_spring.infrastructure.api.dtos.TokenDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

//@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String TOKEN = "/token";
    public static final String REGISTER = "/register";

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public Mono<TokenDto> login(@AuthenticationPrincipal User user) {
        return userService.login(user.getUsername())
                .map(TokenDto::new);
    }
    @PostMapping(produces = {"application/json"})
    public Mono <es.upm.miw.lost_found_spring.domain.model.User> createUser(@Valid @RequestBody es.upm.miw.lost_found_spring.domain.model.User user ){
        user.doDefault();
        return this.userService.createUser(user);
    }
}
