package es.upm.miw.lost_found_spring.domain.model;


import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @NotBlank
    private String userName;
    @NotBlank
    private String familyName;;

    @Indexed(unique = true)
    private String email;

    private String password;
    private Role role;
    private LocalDateTime registrationDate;
    private String photo;

    public void doDefault() {
            if (Objects.isNull(password)) {
                password = UUID.randomUUID().toString();
            }
            if (Objects.isNull(role)) {
                this.role = Role.OPERATOR;
            }
        }

}
