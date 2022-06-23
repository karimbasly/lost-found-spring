package es.upm.miw.lost_found_spring.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Chat {
    private String id;
    private String sendEmailFrom;
    private String sendEmailTo;
    private String lastMessage;
    private String userPhotoFrom;
    private String userPhotoTo;
    private String userNamesFrom;
    private String userNamesTo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateLastMessage;
    private List<Message> message;
}
