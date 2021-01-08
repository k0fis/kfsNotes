package kfs.vueNotes.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Note {
    private long id;

    @Size(max = 120)
    private String title;

    @Size(max = 1000)
    private String description;

    private ZonedDateTime published;

    @Size(max = 30)
    private String status;
}
