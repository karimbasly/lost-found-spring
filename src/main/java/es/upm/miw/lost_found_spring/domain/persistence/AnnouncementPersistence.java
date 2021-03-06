package es.upm.miw.lost_found_spring.domain.persistence;

import es.upm.miw.lost_found_spring.domain.model.Announcement;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AnnouncementPersistence {
    Mono<Announcement> createAnnouncement(Announcement announcement);

    Flux<Announcement> findByTypeAndCategoryLocalisationNullSafe(String category, String type, String location);

    Mono<Announcement> findById(String id);

    Mono<Announcement> updateAnnouncement(String id, Announcement announcement);

    Flux<Announcement> findByUserEmail(String userEmail);

    Mono<Void> delete(String id);
}
