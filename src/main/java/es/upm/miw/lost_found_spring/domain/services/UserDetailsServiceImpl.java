package es.upm.miw.lost_found_spring.domain.services;

import es.upm.miw.lost_found_spring.domain.Persistence.UserPersistence;
import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service("reactiveUserDetailsService")
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private UserPersistence userPersistence;

    @Autowired
    public UserDetailsServiceImpl(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    private Mono<org.springframework.security.core.userdetails.User> userBuilder(String email, String password, Role[] roles) {
        List< GrantedAuthority > authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.withPrefix()));
        }
        return Mono.just(new org.springframework.security.core.userdetails.User(email, password, true, true,
                true, true, authorities));
    }



    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return this.userPersistence.findByEmail(email)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Non existent email: " + email)))
                .flatMap(user -> this.userBuilder(user.getEmail(), user.getPassword(), new Role[]{Role.AUTHENTICATED}));

    }
}
