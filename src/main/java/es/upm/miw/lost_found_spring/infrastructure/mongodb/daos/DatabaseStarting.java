package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos;

import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous.UserDao;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DatabaseStarting {

    private final UserDao userDao;

    private static final String ADMIN = "admin";
    private static final String TEST_PAS = "test";
    private static final String USERNAME = "admin@admin.admin";
    private static final String TEST = "test@test.test";
    private static final String NAME = "karim";
    private static final String VARIOUS_LOCATION = "Madrid";
    private static final Integer POINT_MOBILE = 645490418;
    private static final Integer POINT_MOBILE1 = 645490416;


    @Autowired
    public DatabaseStarting(UserDao userDao) {
        this.userDao = userDao;
        this.initialize();
    }

    void initialize() {
        LogManager.getLogger(this.getClass()).warn("------- Finding Admin -----------");
        if (this.userDao.findByRoleIn(List.of(Role.ADMIN)).isEmpty()) {
            UserEntity[] users = {UserEntity.builder().userName(NAME).familyName(NAME)
                    .email(USERNAME)
                    .password(new BCryptPasswordEncoder().encode(ADMIN))
                    .mobile(POINT_MOBILE1).location(VARIOUS_LOCATION)
                    .photo("")
                    .role(Role.ADMIN).registrationDate(LocalDateTime.now()).build(),
                    UserEntity.builder().userName(NAME).familyName(NAME)
                            .email(TEST)
                            .password(new BCryptPasswordEncoder().encode(TEST_PAS)).photo("").mobile(POINT_MOBILE).location(VARIOUS_LOCATION)
                            .role(Role.CUSTOMER).registrationDate(LocalDateTime.now()).build(),
            };
            this.userDao.saveAll(List.of(users));
            LogManager.getLogger(this.getClass()).warn("------- Created Admin -----------");
        }
    }

}
