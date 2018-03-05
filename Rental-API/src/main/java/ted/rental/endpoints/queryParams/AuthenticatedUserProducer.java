package ted.rental.endpoints.queryParams;

import ted.rental.annotations.AuthenticatedUser;
import ted.rental.model.User;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

import ted.rental.services.UserService;

@RequestScoped
public class AuthenticatedUserProducer {

    private static UserService userService = new UserService();

    @Produces
    @RequestScoped
    @AuthenticatedUser
    private User authenticatedUser;

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
        this.authenticatedUser = findUser(username);
    }

    private User findUser(String username) {
        return userService.getUserByUsername(username);
    }
}
