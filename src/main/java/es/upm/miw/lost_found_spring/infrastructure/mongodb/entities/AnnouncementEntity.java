package es.upm.miw.lost_found_spring.infrastructure.mongodb.entities;

import es.upm.miw.lost_found_spring.domain.model.Announcement;
import es.upm.miw.lost_found_spring.domain.model.Category;
import es.upm.miw.lost_found_spring.domain.model.Type;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Getter
@Setter
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
    private Type type;
    private Category category;
    private String photo;
    private String location;
    private Double lat;
    private Double lng;
    private String userEmail;
    @DBRef(lazy = true)
    private UserEntity userEntity;

    public AnnouncementEntity(Announcement announcement, UserEntity userEntity) {
        BeanUtils.copyProperties(announcement, this);
        this.name = announcement.getName();
        this.userEmail = userEntity.getEmail();
        this.userEntity = userEntity;
    }

    public Announcement toAnnouncement() {
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(this, announcement);
        announcement.setUserEmail(userEntity.getEmail());
        announcement.setUserPhoto(userEntity.getPhoto());
        announcement.setUserName(userEntity.getUserName());
        return announcement;
    }
}
