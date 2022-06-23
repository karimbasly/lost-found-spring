package es.upm.miw.lost_found_spring.infrastructure.mongodb.persistence;

import es.upm.miw.lost_found_spring.domain.exceptions.NotFoundException;
import es.upm.miw.lost_found_spring.domain.model.Chat;
import es.upm.miw.lost_found_spring.domain.model.Message;
import es.upm.miw.lost_found_spring.domain.persistence.ChatPersistence;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.ChatReactive;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.MessageReactive;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.daos.UserReactive;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.ChatEntity;
import es.upm.miw.lost_found_spring.infrastructure.mongodb.entities.MessageEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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
                    chatEntity.setDateLastMessage(LocalDateTime.now());
                    messageEntity1.setSenderEmail(userEntity.getEmail());
                    messageEntity1.setText(chatEntity.getLastMessage());
                    messageEntity1.setMessageDate(LocalDateTime.now());
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

    @Override
    public Flux<Chat> findBySendEmailFrom(String sendEmailFrom, String sendEmailTo) {
        return this.chatReactive.findBySendEmailFromOrSendEmailTo(sendEmailFrom, sendEmailTo)
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Non existing user:To2 " + sendEmailFrom + sendEmailTo)))

                .map(ChatEntity::toChat);
    }

    @Override
    public Mono<Chat> findById(String id) {
        return this.chatReactive.findById(id)
                .map(ChatEntity::toChat);
    }

    @Override
    public Mono<Chat> addMessage(String id, Chat chat) {
        int lastElement = chat.getMessage().size();
        Message message1 = chat.getMessage().get(lastElement - 1);
        MessageEntity messageEntity = new MessageEntity(message1);
        messageEntity.setMessageDate(LocalDateTime.now());
        return this.messageReactive.save(messageEntity)
                .then(this.chatReactive.findById(id))
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Non existing Chat  " + id)))
                .map(chatEntity -> {
                    BeanUtils.copyProperties(chat, chatEntity);
                    chatEntity.setDateLastMessage(LocalDateTime.now());
                    chatEntity.setLastMessage(messageEntity.getText());
                    chatEntity.add(messageEntity);
                    return chatEntity;
                })
                .flatMap(this.chatReactive::save)
                .map(ChatEntity::toChat);

    }
}
