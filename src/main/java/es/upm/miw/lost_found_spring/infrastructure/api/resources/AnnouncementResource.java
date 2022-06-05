package es.upm.miw.lost_found_spring.infrastructure.api.resources;

import es.upm.miw.lost_found_spring.domain.model.Announcement;
import es.upm.miw.lost_found_spring.domain.services.AnnouncementService;
import es.upm.miw.lost_found_spring.infrastructure.api.dtos.AnnouncementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(AnnouncementResource.ANNOUNCEMENT)
public class AnnouncementResource {
    public static final String ANNOUNCEMENT = "/Announcements";
    public static final String SEARCH = "/search";
    public static final String ID_ID = "/{id}";
    public static final String USER_EMAIL = "/{userEmail}";
    public static final String USERS = "/user";


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

    @GetMapping(SEARCH)
    public Flux<AnnouncementDto> findByTypeAndCategoryLocalisationNullSafe(
            @RequestParam(required = false) String category, @RequestParam(required = false) String type,
            @RequestParam(required = false) String location) {
        return this.announcementService.findByTypeAndCategoryLocalisationNullSafe(category, type, location)
                .map(AnnouncementDto::ofvalue);
    }

    @GetMapping(ID_ID)
    public Mono<Announcement> findById(@PathVariable String id) {
        return this.announcementService.findById(id);
    }


    @PutMapping(ID_ID)
        public Mono<Announcement>updateAnnouncement(@PathVariable String id,@Valid @RequestBody Announcement announcement){
            return this.announcementService.updateAnnouncement(id,announcement);
    }


    @GetMapping(USERS + USER_EMAIL)
    public Flux<AnnouncementDto> findByUserEmail(@PathVariable String userEmail) {
        return this.announcementService.findByUserEmail(userEmail)
                .map(AnnouncementDto::ofvalue);
    }


    @DeleteMapping(ID_ID)
    public Mono<Void> deleteAnnouncement(@PathVariable String id) {
        return this.announcementService.delete(id);
    }


}
