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

    private UserDao userDao;

    private static final String ADMIN = "admin";
    private static final String USERNAME = "9";
    private static final String PASSWORD = "9";
    private static final String NAME = "aaa";


    @Autowired
    public DatabaseStarting(UserDao userDao) {
        this.userDao = userDao;
        this.initialize();
    }

    void initialize() {
        LogManager.getLogger(this.getClass()).warn("------- Finding Admin -----------");
        if (this.userDao.findByRoleIn(List.of(Role.ADMIN)).isEmpty()) {
            UserEntity user = UserEntity.builder().userName(NAME).familyName(NAME)
                    .email(USERNAME)
                    .password(new BCryptPasswordEncoder().encode(PASSWORD))
                    .role(Role.ADMIN).registrationDate(LocalDateTime.now()).build();
            this.userDao.save(user);
            LogManager.getLogger(this.getClass()).warn("------- Created Admin -----------");
        }
    }

}
