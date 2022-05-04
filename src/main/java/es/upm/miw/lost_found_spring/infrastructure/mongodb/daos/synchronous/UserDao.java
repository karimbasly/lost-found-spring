package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous;

import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface UserDao extends MongoRepository<UserEntity, String> {
    List<UserEntity> findByRoleIn(Collection<Role> roles);
}
