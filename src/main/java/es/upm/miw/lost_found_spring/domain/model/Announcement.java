package es.upm.miw.lost_found_spring.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Announcement {
    private String id;
    @NotBlank
    private String name;
    private String description;
    private Type type;
    private Category category;
    private String photo;
    private String location;
    private Double lat;
    private Double lng;
    @NotBlank
    private String userEmail;
    private String userPhoto;
    private String userName;


    public void toDefault() {
        if (Objects.isNull(photo)) {
            this.photo = " ";
        }
        if (Objects.isNull(lat)) {
            this.lat = 0.0;
        }
        if (Objects.isNull(lng)) {
            this.lng = 0.0;
        }
        if (Objects.isNull(location)) {
            this.location = "";
        }

    }
    //TODO UNITEST
}
