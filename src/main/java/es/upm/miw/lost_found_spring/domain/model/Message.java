package es.upm.miw.lost_found_spring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
//@JsonDeserialize
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    private String id;
    private String senderEmail;
    private String text;

    public void doDefault() {
        if (Objects.isNull(senderEmail)) {
            senderEmail = "LocalDateTime.now()";
        }
        if (Objects.isNull(text)) {
            this.text = "Role.CUSTOMER";
        }
    }
}
