package es.upm.miw.lost_found_spring.infrastructure.mongodb.daos;

import es.upm.miw.lost_found_spring.domain.model.Category;
import es.upm.miw.lost_found_spring.domain.model.Type;
import es.upm.miw.lost_found_spring.infrastructure.api.http_errors.Role;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous.AnnouncementDao;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous.ChatDao;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous.MessageDao;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.synchronous.UserDao;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.AnnouncementEntity;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.ChatEntity;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.MessageEntity;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service // @Profile("dev")
public class DatabaseSeederDev {


    private final DatabaseStarting databaseStarting;
    private final UserDao userDao;
    private final AnnouncementDao announcementDao;
    private final MessageDao messageDao;
    private final ChatDao chatDao;

    @Autowired
    public DatabaseSeederDev(DatabaseStarting databaseStarting, UserDao userDao, AnnouncementDao announcementDao, MessageDao messageDao, ChatDao chatDao) {
        this.databaseStarting = databaseStarting;
        this.userDao = userDao;
        this.announcementDao = announcementDao;
        this.messageDao = messageDao;
        this.chatDao = chatDao;
        this.deleteAllAndInitializeAndSeedDataBase();

    }

    public void deleteAllAndInitializeAndSeedDataBase() {

        this.deleteAllAndInitialize();
        this.seedDataBaseJava();
    }

    private void deleteAllAndInitialize() {
        LogManager.getLogger(this.getClass()).warn("------- Delete All -------");
        this.userDao.deleteAll();
        this.announcementDao.deleteAll();
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
        //this.userDao.deleteAll(List.of(users));
        LogManager.getLogger(this.getClass()).warn("        ------- users");
        AnnouncementEntity[] announcementEntities = {
                AnnouncementEntity.builder().id("id1").category(Category.PETS).description("des").location("tunis")
                        .name("Cat").photo("").type(Type.LOST).userEntity(users[0])
                        .build(),
        };
        this.announcementDao.saveAll(List.of(announcementEntities));
        //this.announcementDao.deleteAll(List.of(announcementEntities));
        LogManager.getLogger(this.getClass()).warn("        ------- AnnouncementEntity");

        List<MessageEntity> messages = Arrays.asList(
                MessageEntity.builder().text("hello").senderEmail("aa@aa.a").id("id1").build(),
                MessageEntity.builder().text("hello").senderEmail("aa@aa.a").id("id2").build()

        );
        this.messageDao.saveAll(messages);

        List<ChatEntity> chatEntities = Arrays.asList(
                ChatEntity.builder().id("id1").sendEmailFrom("karim1").sendEmailTo("karim").lastMessage("ok").userPhotoFrom("ok")
                        .userPhotoTo("ok").userNamesFrom("ok").userNamesTo("ok").messageEntities(messages).build(),
                //private String lastMessageDate;

                ChatEntity.builder().id("id2").sendEmailFrom("karim2").sendEmailTo("karim1").lastMessage("ok1").userPhotoFrom("ok")
                        .userPhotoTo("ok").userNamesFrom("ok").userNamesTo("ok").messageEntities(messages).build()
        );

        this.chatDao.saveAll(chatEntities);
    }

}