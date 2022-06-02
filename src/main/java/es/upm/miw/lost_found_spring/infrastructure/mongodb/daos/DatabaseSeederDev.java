package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos;

import es.upm.miw.lost_found_spring.domain.model.Category;
import es.upm.miw.lost_found_spring.domain.model.Type;
import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous.AnnouncementDao;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous.UserDao;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.AnnouncementEntity;
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

    private final DatabaseStarting databaseStarting;
    private final UserDao userDao;
    private final AnnouncementDao announcementDao;

    @Autowired
    public DatabaseSeederDev(DatabaseStarting databaseStarting, UserDao userDao, AnnouncementDao announcementDao) {
        this.databaseStarting = databaseStarting;
        this.userDao = userDao;
        this.announcementDao = announcementDao;
        this.deleteAllAndInitializeAndSeedDataBase();

    }

    public void deleteAllAndInitializeAndSeedDataBase() {

        this.deleteAllAndInitialize();
        this.seedDataBaseJava();
    }

    private void deleteAllAndInitialize() {
        LogManager.getLogger(this.getClass()).warn("------- Delete All -------");
        //this.userDao.deleteAll();
        //this.announcementDao.deleteAll();
        this.databaseStarting.initialize();
    }

    private void seedDataBaseJava() {
        LogManager.getLogger(this.getClass()).warn("------- Initial Load from JAVA --------");
        String pass = new BCryptPasswordEncoder().encode("9");
        UserEntity[] users = {
                UserEntity.builder().userName("abc").email("karim@a.a").familyName("Name1")
                        .mobile(12365).location("Madrid")
                        .password(pass)
                        .role(Role.CUSTOMER).registrationDate(LocalDateTime.now()).build(),
        };
        this.userDao.saveAll(List.of(users));
        this.userDao.deleteAll(List.of(users));
        LogManager.getLogger(this.getClass()).warn("        ------- users");
        UserEntity userEntity = UserEntity.builder().id("id35").userName("test").email("test@a.a").familyName("Name1")
                .mobile(12365).location("Spain")
                .password(pass)
                .role(Role.CUSTOMER).registrationDate(LocalDateTime.now()).build();
        AnnouncementEntity[] announcementEntities = {
                AnnouncementEntity.builder().id("id1").category(Category.PETS).description("des").location("tunis")
                        .name("Cat").photo("").type(Type.LOST).userEntity(userEntity)
                        .build(),
        };
        this.announcementDao.saveAll(List.of(announcementEntities));
        this.announcementDao.deleteAll(List.of(announcementEntities));
        LogManager.getLogger(this.getClass()).warn("        ------- AnnouncementEntity");
    }

}