package es.upm.miw.lost_found_spring.infrastructure.api.dtos;

import es.upm.miw.lost_found_spring.domain.model.User;
import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import lombok.*;
import org.springframework.beans.BeanUtils;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {


    private String userName;
    private String familyName;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime registrationDate;

public UserDto (User user){
    BeanUtils.copyProperties(user,this);
}
    public static UserDto  toUser(User user) {
    return UserDto.builder()
            .userName(user.getUserName())
            .familyName(user.getFamilyName())
            .email(user.getEmail())
            .role(user.getRole())
            .password(user.getPassword())
            .registrationDate(user.getRegistrationDate())
            .build();
    }
}
