package es.upm.miw.lost_found_spring.infrastructure.mongodb.entities;

import es.upm.miw.lost_found_spring.domain.model.Message;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MessageEntity {
    @Id
    @Generated
    private String id;
    private String senderEmail;
    private String text;

    public MessageEntity(Message message) {
        BeanUtils.copyProperties(message, this);
    }


    public Message toMessage() {
        Message message = new Message();
        BeanUtils.copyProperties(this, message);
        return message;
    }
}
