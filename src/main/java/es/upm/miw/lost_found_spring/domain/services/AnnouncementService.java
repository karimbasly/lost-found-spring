package es.upm.miw.lost_found_spring.domain.services;

import es.upm.miw.lost_found_spring.domain.Persistence.AnnouncementPersistence;
import es.upm.miw.lost_found_spring.domain.model.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnnouncementService {

    private final AnnouncementPersistence announcementPersistence;

    @Autowired
    public AnnouncementService(AnnouncementPersistence announcementPersistence) {
        this.announcementPersistence = announcementPersistence;
    }

    public Mono<Announcement> createAnnouncement(Announcement announcement) {
        return this.announcementPersistence.createAnnouncement(announcement);
    }

    public Flux<Announcement> findByTypeAndCategoryLocalisationNullSafe(String category, String type, String location) {
        return this.announcementPersistence.findByTypeAndCategoryLocalisationNullSafe(category, type, location);
    }

    public Mono<Announcement> findById(String id) {
        return this.announcementPersistence.findById(id);
    }

    public Flux<Announcement> findByUserEmail(String userEmail) {
        return this.announcementPersistence.findByUserEmail(userEmail);
    }


    public Mono<Announcement> updateAnnouncement(String id, Announcement announcement) {
        return this.announcementPersistence.updateAnnouncement(id, announcement);
    }


    public Mono<Void> delete(String id) {
        return this.announcementPersistence.delete(id);
    }
}

