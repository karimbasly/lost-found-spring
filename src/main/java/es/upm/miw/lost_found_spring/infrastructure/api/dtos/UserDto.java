package es.upm.miw.lost_found_spring.infrastructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.upm.miw.lost_found_spring.domain.model.User;
import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {


    private String userName;
    private String familyName;
    private String email;
    private Integer mobile;
    private String location;
    private Role role;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;


    public static UserDto ofNameEmailMobile(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .userName(user.getUserName())
                .familyName(user.getFamilyName())
                .mobile(user.getMobile())
                .location(user.getLocation())
                .registrationDate(user.getRegistrationDate())
                .role(user.getRole())
                .build();
    }
}
