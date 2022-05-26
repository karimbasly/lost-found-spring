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

}
