package ted.rental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import ted.rental.annotations.Role;
import ted.rental.database.entities.UserEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

import static ted.rental.config.Constraint.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class User {

    @NotNull
    @Length(min = USERNAME_MIN, max = USERNAME_MAX)
    @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_MESSAGE)
    private String username;

    @NotNull
    @Length(min = PASSWORD_MIN, max = PASSWORD_MAX)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_PATTERN_MESSAGE)
    @JsonIgnore
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Role role;

    private Timestamp created;

    public User() {

    }

    /*OK -- POST Constructor*/
    public User(final String username, final String password, final String email, final Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        //this.created = new Timestamp(System.currentTimeMillis());
    }

    /*OK -- GET Constructor*/
    public User(final String username, final String password, final String email, final Role role, final Timestamp created) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.created = created;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public abstract Role getRole();

    public abstract Boolean getConfirmed();

    public void setRole(Role role) {
        this.role = role;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + this.getRole() +
                ", created=" + created +
                '}';
    }

    public abstract UserEntity toEntity();

}
