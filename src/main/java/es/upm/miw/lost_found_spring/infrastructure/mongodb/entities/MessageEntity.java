package es.upm.miw.lost_found_spring.infrastructure.mongodb.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.upm.miw.lost_found_spring.domain.model.Message;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MessageEntity {
    @Id
    @Generated
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime messageDate;
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
