package kfs.vueNotes.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.ZonedDateTime;

@Entity
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class KNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private long userId;

    @NotNull
    @NotEmpty@Size(min=2, max = 120)
    @Length(min = 2, max = 120)
    private String title;

    @Size(max = 1000)
    @Length(max = 1000)
    private String description;

    @NotNull
    private ZonedDateTime published;

    @NotNull@NotEmpty
    @Size(max = 30)
    @Length(max = 30)
    private String status;
}
