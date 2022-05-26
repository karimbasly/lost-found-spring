package es.upm.miw.lost_found_spring.infrastructure.api.dtos;

import es.upm.miw.lost_found_spring.domain.model.Announcement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AnnouncementDto {

    private String id;
    private String name;
    private String description;
    private String photo;
    private String location;
    private String username;
    private String userPhoto;

    public static AnnouncementDto ofvalue(Announcement announcement) {
        return
                AnnouncementDto.builder()
                        .id(announcement.getId())
                        .name(announcement.getName())
                        .description(announcement.getDescription())
                        .photo(announcement.getPhoto())
                        .location(announcement.getLocation())
                        .username(announcement.getUserName())
                        .userPhoto(announcement.getUserPhoto())
                        .build();


    }
}
