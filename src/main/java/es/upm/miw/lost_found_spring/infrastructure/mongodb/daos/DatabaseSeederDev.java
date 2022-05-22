package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos;

import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous.UserDao;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service // @Profile("dev")
public class DatabaseSeederDev {
    // private RepoDao repoDao;

    private DatabaseStarting databaseStarting;
    private UserDao userDao;

    @Autowired
    public DatabaseSeederDev(DatabaseStarting databaseStarting, UserDao userDao) {
        this.databaseStarting = databaseStarting;
        this.userDao = userDao;
        this.deleteAllAndInitializeAndSeedDataBase();

    }

    public void deleteAllAndInitializeAndSeedDataBase() {

        this.deleteAllAndInitialize();
        this.seedDataBaseJava();
    }

    private void deleteAllAndInitialize() {
        LogManager.getLogger(this.getClass()).warn("------- Delete All -------");
        this.userDao.deleteAll();
        this.databaseStarting.initialize();
    }

    private void seedDataBaseJava() {
        LogManager.getLogger(this.getClass()).warn("------- Initial Load from JAVA --------");
        String pass = new BCryptPasswordEncoder().encode("9");
        UserEntity[] users = {
                UserEntity.builder().userName("110").email("10").familyName("Name1")
                        .password(pass)
                        .role(Role.CUSTOMER).registrationDate(LocalDateTime.now()).build(),
        };
        this.userDao.saveAll(List.of(users));
        LogManager.getLogger(this.getClass()).warn("        ------- users");
    }

}