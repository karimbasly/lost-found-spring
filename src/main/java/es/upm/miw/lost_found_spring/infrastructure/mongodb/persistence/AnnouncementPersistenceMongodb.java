package es.upm.miw.lost_found_spring.infrastructure.mongodb.persistence;

import es.upm.miw.lost_found_spring.domain.Persistence.AnnouncementPersistence;
import es.upm.miw.lost_found_spring.domain.exceptions.NotFoundException;
import es.upm.miw.lost_found_spring.domain.model.Announcement;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.AnnouncementReactive;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.UserReactive;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.AnnouncementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AnnouncementPersistenceMongodb implements AnnouncementPersistence {

    private final AnnouncementReactive announcementReactive;
    private final UserReactive userReactive;

    @Autowired
    public AnnouncementPersistenceMongodb(AnnouncementReactive announcementReactive, UserReactive userReactive) {
        this.announcementReactive = announcementReactive;
        this.userReactive = userReactive;
    }

    @Override
    public Mono<Announcement> createAnnouncement(Announcement announcement) {

        return

                this.userReactive.findByEmail(announcement.getUserEmail())
                        .switchIfEmpty(Mono.error(new NotFoundException("Non existing user: " + announcement.getUserEmail())
                        ))
                        .map(userEntity -> new AnnouncementEntity(announcement, userEntity))

                        .flatMap(this.announcementReactive::save)
                        .map(AnnouncementEntity::toAnnouncement);


    }

    @Override
    public Flux<Announcement> findByTypeAndCategoryLocalisationNullSafe(String category, String type, String location) {
        return this.announcementReactive.findByTypeAndCategoryLocalisationNullSafe(category, type, location)
                .map(AnnouncementEntity::toAnnouncement);
    }

    @Override
    public Mono<Announcement> findById(String id) {
        return this.announcementReactive.findById(id)
                .map(AnnouncementEntity::toAnnouncement);
    }
/*
    @Override
    public Mono<Announcement> updateAnnouncement(String id, Announcement announcement) {
        Mono<AnnouncementEntity> announcementEntityMono;
        announcementEntityMono=this.announcementReactive.findById(id);
        return announcementEntityMono
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent user id: " + id)))
                .map(announcementEntity -> {
                    BeanUtils.copyProperties(announcement,announcementEntity );
                    return announcementEntity;
                })
                .flatMap(this.announcementReactive::save)
                .map(AnnouncementEntity::toAnnouncement);
    }

 */

    @Override
    public Flux<Announcement> findByUserEmail(String userEmail) {
        return this.announcementReactive.findByUserEmail(userEmail)
                .map(AnnouncementEntity::toAnnouncement);
    }


}
