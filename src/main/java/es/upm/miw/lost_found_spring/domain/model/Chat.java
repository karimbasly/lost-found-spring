package es.upm.miw.lost_found_spring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Chat {
    private String id;
    private String sendEmailFrom;
    private String sendEmailTo;
    private String lastMessage;
    //private String lastMessageDate;
    private String userPhotoFrom;
    private String userPhotoTo;
    private String userNamesFrom;
    private String userNamesTo;
    @DBRef
    private List<Message> message;


    public void doDefault() {
        if (Objects.isNull(sendEmailFrom)) {
            sendEmailFrom = "LocalDateTime.now();";
        }
        if (Objects.isNull(sendEmailTo)) {
            sendEmailTo = "Role.CUSTOMER";
        }

        if (Objects.isNull(lastMessage)) {
            lastMessage = "";
        }
        if (Objects.isNull(userPhotoFrom)) {
            userPhotoFrom = "";
        }
        if (Objects.isNull(userPhotoTo)) {
            userPhotoTo = "";
        }
        if (Objects.isNull(userNamesFrom)) {
            userNamesFrom = "";
        }
        if (Objects.isNull(userNamesTo)) {
            userNamesTo = "";
        }

    }
}
