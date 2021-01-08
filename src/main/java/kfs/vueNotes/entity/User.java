package kfs.vueNotes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = {
       @UniqueConstraint(columnNames = "username"),
       @UniqueConstraint(columnNames = "email")
   })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    @Size(min=2, max = 120)
    @Length(min = 2, max = 120)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min=4, max = 200)
    @Length(min = 4, max = 200)
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=4, max = 200)
    @Length(min = 4, max = 200)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(@NotNull @NotEmpty @Size(min = 2, max = 120) @Length(min = 2, max = 120) String username, @NotNull @NotEmpty @Size(min = 4, max = 200) @Length(min = 4, max = 200) String email, @NotNull @NotEmpty @Size(min = 4, max = 200) @Length(min = 4, max = 200) String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
