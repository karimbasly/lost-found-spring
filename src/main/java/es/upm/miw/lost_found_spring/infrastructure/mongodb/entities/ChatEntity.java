package es.upm.miw.lost_found_spring.infrastructure.mongodb.entities;

import es.upm.miw.lost_found_spring.domain.model.Chat;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ChatEntity {
    @Id
    @Generated
    private String id;
    @NotBlank
    private String sendEmailFrom;
    @NotBlank
    private String sendEmailTo;
    private String lastMessage;
    //private String lastMessageDate;
    private String userPhotoFrom;
    private String userPhotoTo;
    private String userNamesFrom;
    private String userNamesTo;
    @DBRef(lazy = true)
    private List<MessageEntity> messageEntities;

    public ChatEntity(Chat chat) {
        BeanUtils.copyProperties(chat, this);
        this.messageEntities = new ArrayList<>();

    }

    public void add(MessageEntity messageEntity) {
        this.messageEntities.add(messageEntity);
    }

    public Chat toChat() {
        Chat chat = new Chat();
        BeanUtils.copyProperties(this, chat);
        chat.setMessage(this.getMessageEntities().stream()
                .map(MessageEntity::toMessage)
                .collect(Collectors.toList())

        );

        return chat;
    }
}
