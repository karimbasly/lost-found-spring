package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous;

import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.AnnouncementEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnnouncementDao extends MongoRepository<AnnouncementEntity, String> {
}
