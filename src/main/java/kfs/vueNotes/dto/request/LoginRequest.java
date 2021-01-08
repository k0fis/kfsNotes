package kfs.vueNotes.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
