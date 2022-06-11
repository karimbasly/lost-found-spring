package es.upm.miw.lost_found_spring.domain.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private String id;
    @NotBlank
    private String userName;
    @NotBlank
    private String familyName;

    @NotBlank
    @Indexed(unique = true)
    private String email;
    @NotBlank
    private String password;
    private Role role;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;
    private String photo;

    private Integer mobile;
    @NotBlank
    private String location;

    public void doDefault() {
        if (Objects.isNull(registrationDate)) {
            registrationDate = LocalDateTime.now();
        }
        if (Objects.isNull(role)) {
            this.role = Role.CUSTOMER;
        }

        if (Objects.isNull(photo)) {
            photo = "";
        }
    }
    //TODO UNITEST
}
