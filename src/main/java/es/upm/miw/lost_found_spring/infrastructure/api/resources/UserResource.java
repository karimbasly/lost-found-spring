package es.upm.miw.lost_found_spring.infrastructure.api.resources;

import es.upm.miw.lost_found_spring.domain.model.User;
import es.upm.miw.lost_found_spring.domain.services.UserService;
import es.upm.miw.lost_found_spring.infrastructure.api.dtos.TokenDto;
import es.upm.miw.lost_found_spring.infrastructure.api.dtos.UserDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

//@PreAuthorize("hasRole('ADMIN')")
//@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String TOKEN = "/token";
    public static final String SEARCH = "/search";
    public static final String EMAIL = "/{email}";

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public Mono<TokenDto> login(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        return userService.login(user.getUsername())
                .map(TokenDto::new);
    }

    @PreAuthorize("permitAll()")
    @PostMapping(produces = {"application/json"})
    public Mono<User> createUser(@Valid @RequestBody User user) {
        user.doDefault();
        return this.userService.createUser(user);
    }


    @GetMapping(EMAIL)
    public Mono<User> GetUser(@PathVariable String email) {
        return this.userService.readByEmail(email);
    }


    @PutMapping(EMAIL)
    public Mono<User> update(@PathVariable String email, @Valid @RequestBody User user) {
        return this.userService.update(email, user);
    }

    @GetMapping(SEARCH)
    public Flux<UserDto> findByUserNameAndEmailAndMobileAndLocalisationNullSafe(
            @RequestParam(required = false) String userName, @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer mobile, @RequestParam(required = false) String location) {
        return this.userService.findByNameAndEmailAndLocalisationNullSafe(userName, email, mobile, location)
                .map(UserDto::ofNameEmailMobile);
    }

    @DeleteMapping(EMAIL)
    public Mono<Void> deleteUser(@PathVariable String email) {
        return this.userService.delete(email);
    }
}
