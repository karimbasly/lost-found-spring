package es.upm.miw.lost_found_spring.infrastructure.mongodb.entities;

import es.upm.miw.lost_found_spring.domain.model.User;
import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class UserEntity {
    @Id
    @Generated
    private String id;
    @NonNull
    private String userName;
    @NonNull
    private String familyName;
    @NonNull
    @Indexed(unique = true)
    private String email;
    @NonNull
    private String password;
    private Role role;
    private LocalDateTime registrationDate;
    private String photo;

    private int mobile;
    private String location;


    public User toUser() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }


}

