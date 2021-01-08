package kfs.vueNotes.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtResponse {
    private String token;
    private String type = "Kofis";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
