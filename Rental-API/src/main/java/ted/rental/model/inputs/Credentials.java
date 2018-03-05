package ted.rental.model.inputs;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import static ted.rental.config.Constraint.*;

public class Credentials implements Serializable {

    @NotNull
    @Length(min = USERNAME_MIN, max = USERNAME_MAX)
    @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_MESSAGE)
    private String username;

    @NotNull
    @Length(min = PASSWORD_MIN, max = PASSWORD_MAX)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_PATTERN_MESSAGE)
    private String password;

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
}
