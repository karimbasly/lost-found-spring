package es.upm.miw.lost_found_spring.infrastructure.api.dtos;

import es.upm.miw.lost_found_spring.domain.model.Announcement;
import es.upm.miw.lost_found_spring.domain.model.Category;
import es.upm.miw.lost_found_spring.domain.model.Type;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AnnouncementDto {

    private String id;
    private String name;
    private String description;
    private Type type;
    private Category category;
    private String photo;
    private String location;
    private String userEmail;
    private String username;
    private String userPhoto;

    public static AnnouncementDto ofvalue(Announcement announcement) {
        return AnnouncementDto.builder()
                .id(announcement.getId())
                .name(announcement.getName())
                .description(announcement.getDescription())
                .category(announcement.getCategory())
                .type(announcement.getType())
                .photo(announcement.getPhoto())
                .location(announcement.getLocation())
                .userEmail(announcement.getUserEmail())
                .username(announcement.getUserName())
                .userPhoto(announcement.getUserPhoto())
                .build();


    }
}
