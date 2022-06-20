package es.upm.miw.lost_found_spring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

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
}
