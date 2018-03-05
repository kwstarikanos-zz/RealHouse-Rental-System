package ted.rental.model.inputs;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import ted.rental.model.Profile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import static ted.rental.config.Constraint.*;

public class Register implements Serializable {

    @NotNull
    @Length(min = USERNAME_MIN, max = USERNAME_MAX)
    @Pattern(regexp = USERNAME_PATTERN)
    private String username;

    @NotNull
    @Length(min = PASSWORD_MIN, max = PASSWORD_MAX)
    @Pattern(regexp = PASSWORD_PATTERN)
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Boolean host;

    //@NotNull
    @Valid
    private Profile profile;

    public Register() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Boolean getHost() {
        return host;
    }

    public void setHost(Boolean host) {
        this.host = host;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Register{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", host=" + host +
                ", profile=" + profile +
                '}';
    }
}
