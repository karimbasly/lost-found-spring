package es.upm.miw.lost_found_spring.infrastructure.mongodb.entities;

import es.upm.miw.lost_found_spring.domain.model.Announcement;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class AnnouncementEntity {
    @Id
    @Generated
    private String id;
    @NonNull
    private String name;
    private String description;
    private String type;
    private String Category;
    private String photo;
    private String location;
    private Double lat;
    private Double lng;
    @DBRef
    private UserEntity userEntity;

    public Announcement toAnnouncement() {
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(this, announcement);
        announcement.setUserEmail(userEntity.getEmail());
        announcement.setUserPhoto(userEntity.getPhoto());
        announcement.setUserName(userEntity.getUserName());
        return announcement;
    }
}
