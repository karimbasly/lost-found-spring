package es.upm.miw.lost_found_spring.infrastructure.api.resources;

import es.upm.miw.lost_found_spring.domain.model.Announcement;
import es.upm.miw.lost_found_spring.domain.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(AnnouncementResource.ANNOUNCEMENT)
public class AnnouncementResource {
    public static final String ANNOUNCEMENT = "/Announcements";
    public static final String SEARCH = "/search";


    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementResource(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<Announcement> createAnnouncement(@Valid @RequestBody Announcement announcement) {
        announcement.toDefault();
        return this.announcementService.createAnnouncement(announcement);
    }
/*
    @PreAuthorize("permitAll()")
    @GetMapping()
    public Flux<AnnouncementDto> findByTypeAndCategoryLocalisationNullSafe() {
        return this.announcementService.readAll()

       // return this.announcementService.findByTypeAndCategoryLocalisationNullSafe(category, type, location);
.map(AnnouncementDto::ofvalue);
    }


 */
}
