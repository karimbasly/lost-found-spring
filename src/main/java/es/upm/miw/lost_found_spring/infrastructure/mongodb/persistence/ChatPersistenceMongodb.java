package es.upm.miw.lost_found_spring.infrastructure.mongodb.persistence;

import es.upm.miw.lost_found_spring.domain.exceptions.NotFoundException;
import es.upm.miw.lost_found_spring.domain.model.Chat;
import es.upm.miw.lost_found_spring.domain.persistence.ChatPersistence;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.ChatReactive;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.MessageReactive;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.UserReactive;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.ChatEntity;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ChatPersistenceMongodb implements ChatPersistence {
    private final UserReactive userReactive;
    private final ChatReactive chatReactive;
    private final MessageReactive messageReactive;

    @Autowired
    public ChatPersistenceMongodb(UserReactive userReactive, ChatReactive chatReactive, MessageReactive messageReactive) {
        this.userReactive = userReactive;
        this.chatReactive = chatReactive;
        this.messageReactive = messageReactive;
    }


    @Override
    public Mono<Chat> createChat(Chat chat) {
        ChatEntity chatEntity = new ChatEntity(chat);
        MessageEntity messageEntity1 = new MessageEntity();
        return this.userReactive.findByEmail(chat.getSendEmailFrom())
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Non existing user:From 1 " + chat.getSendEmailFrom())))
                .map(userEntity -> {
                    chatEntity.setUserNamesFrom(userEntity.getUserName());
                    chatEntity.setUserPhotoFrom(userEntity.getPhoto());
                    messageEntity1.setSenderEmail(userEntity.getEmail());
                    messageEntity1.setText(chatEntity.getLastMessage());
                    chatEntity.add(messageEntity1);
                    return messageEntity1;
                }).then(this.messageReactive.save(messageEntity1))
                .then(this.userReactive.findByEmail(chat.getSendEmailTo()))
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Non existing user:To2 " + chat.getSendEmailTo())))
                .map(userEntity1 ->
                {
                    chatEntity.setUserNamesTo(userEntity1.getUserName());
                    chatEntity.setUserPhotoTo(userEntity1.getPhoto());
                    return chatEntity;
                })
                .flatMap(this.chatReactive::save)
                .map(ChatEntity::toChat);
    }
}

                         /*flatMap(chatEntity1 -> {
                    return Flux.fromStream(chat.getMessage().stream())
                            .map(message -> {MessageEntity messageEntity= new MessageEntity(message);
                                return this.userReactive.findByEmail(message.getSenderEmail())
                                        .switchIfEmpty(Mono.error(
                                                new NotFoundException("Non existing user:To2 " + chat.getSendEmailTo())))
                                        .map(userEntity -> {messageEntity.setSenderEmail(userEntity.getEmail());
                                        return messageEntity;
                                        });

                            }).doOnNext(ChatEntity::add)

                         })

/*/
 /*               .flatMap(chatEntity1 -> {
                    chatEntity1.getMessageEntities().stream().map(messageEntity ->{

                           // .map(messageEntity ->messageEntity
                            MessageEntity messageEntity1 =new MessageEntity(messageEntity.toMessage());
                            chatEntity1.add(messageEntity1);
                            return chatEntity1;
                            //return messageEntity1;
                    });
                })*/
//.then(this.chatReactive.save(chatEntity))
//  messageEntity1.setText(chatEntity.getLastMessage());
// messageEntity1.setSenderEmail(chat.getSendEmailFrom());
//chatEntity.add(messageEntity1);
// return
              /*  Flux.fromStream(chat.getMessage().stream())
                        //.switchIfEmpty(Mono.error(new NotFoundException("Article Loss is empty :")))
                .flatMap(message -> {
                    MessageEntity messageEntity =new MessageEntity(message);
                    return this.userReactive.findByEmail(chat.getSendEmailFrom())
                            .switchIfEmpty(Mono.error(
                                    new NotFoundException("Non existing user:From 1 " + message.getSenderEmail())))
                            .map(userEntity ->
                            {messageEntity.setSenderEmail(userEntity.getEmail());
                            messageEntity.setText(chatEntity.getLastMessage());
                                //chatEntity.add(messageEntity);

                            return messageEntity;
                            });

                }).flatMap(this.messageReactive::save)
                        .doOnNext(chatEntity::add)
               .then(this.userReactive.findByEmail(chat.getSendEmailTo()))
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Non existing user:To2 " + chat.getSendEmailTo())))
                .map(userEntity1 ->
                {chatEntity.setUserNamesTo(userEntity1.getUserName());
                    chatEntity.setUserPhotoTo(userEntity1.getPhoto());
                    return chatEntity;
                })
                .then(this.userReactive.findByEmail(chat.getSendEmailFrom()))
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Non existing user:From 1 " + chat.getSendEmailFrom())))
                .map(userEntity3 -> {
                    chatEntity.setUserNamesFrom(userEntity3.getUserName());
                    chatEntity.setUserPhotoFrom(userEntity3.getPhoto());
                    return chatEntity;
                }) .then(this.chatReactive.save(chatEntity))
                        .map(ChatEntity::toChat);

                //.then(this.chatReactive.save(chatEntity))
                .map(ChatEntity::toChat);

                */