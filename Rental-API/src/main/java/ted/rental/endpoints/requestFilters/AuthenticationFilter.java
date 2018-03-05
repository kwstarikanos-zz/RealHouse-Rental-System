package ted.rental.endpoints.requestFilters;

import java.io.IOException;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import java.security.Key;
import java.security.Principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Priorities;

import org.springframework.beans.factory.annotation.Autowired;
import ted.rental.annotations.AuthenticatedUser;
import ted.rental.annotations.Role;
import ted.rental.annotations.Secured;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.User;
import ted.rental.services.*;
import ted.rental.utilities.KeyHolder;

import static javax.ws.rs.core.Response.Status.*;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private Class<?> resourceClass;

    private Method resourceMethod;

    private static UserService userService = new UserService();
    private static RoomService roomService = new RoomService();
    private static CommentService commentService = new CommentService();
    private static ReviewService reviewService = new ReviewService();
    private static ProfileService profileService = new ProfileService();
    private static MessageService messageService = new MessageService();
    private static SessionService session = new SessionService();

    private String validateToken(final String token) {
        Key key = KeyHolder.key;
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new ErrorException(UNAUTHORIZED, e.getMessage());
        }
        return claims.getSubject();
    }

    private List<Role> extractRoles(final AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<Role>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<Role>();
            } else {
                Role[] allowedRoles = secured.roles();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(final List<Role> allowedRoles, final User authenticatedUser, final String owner) {
        boolean permissions = false;

        Role userRole = authenticatedUser.getRole();

/*        Role role = Role.renter;
        if (!authenticatedUser.getConfirmed()) {    //TODO, an einai host kai unconfirmed tote mono ta dikaiwmata toy renter
            userRole = role;
        }*/
        for (Role role : allowedRoles) {
            switch (role) {
                case admin:
                    permissions = userRole.equals(role);
                    break;
                case owner:
                    permissions = authenticatedUser.getUsername().equals(owner);
                    break;
                case renter:
                    permissions = userRole.equals(role);
                    break;
                case host:
                    permissions = userRole.equals(role);
                    break;
            }
            if (permissions)
                break;
        }
        if (!permissions)
            throw new ErrorException(FORBIDDEN, "Sorry, you don't have permissions at this resource! Allowed users: "
                    + allowedRoles.toString());
    }

    private User retrieveAuthenticatedUser(final String username) {
        return userService.getUserByUsername(username);
    }

    private String retrieveRoomOwner(final Integer room_id) {
        return roomService.retrieveRoomOwner(room_id);
    }

    private String retrieveReservationOwner(Integer reservation_id) {
        return roomService.retrieveReservationOwner(reservation_id);
    }

    private String retrieveCommentOwner(Integer comment_id) {
        return commentService.retrieveCommentOwner(comment_id);
    }

    private String retrieveReviewOwner(Integer review_id) {
        return reviewService.retrieveReviewOwner(review_id);
    }

    private String retrieveMessageOwner(Integer message_id, String username) {
        return messageService.retrieveMessageOwner(message_id, username);
    }

    private String retrievePictureOwner(Integer picture_id) {
        return profileService.retrievePictureOwner(picture_id);
    }

    private String extractAuhtorizationBearer(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new ErrorException(BAD_REQUEST, "Authorization header must be provided");
        return authorizationHeader.substring("Bearer".length()).trim();
    }


    @Autowired
    @AuthenticatedUser
    Event<String> userAuthenticatedEvent;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final MultivaluedMap<String, String> pathParameters = requestContext.getUriInfo().getPathParameters();

        /*Validate Token*/
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = extractAuhtorizationBearer(authorizationHeader);

        final String username = validateToken(token);

        if (!session.isActive(username, token)) {
            throw new ErrorException(Response.Status.UNAUTHORIZED, "To be able to access this resource, you must be logged in!");
        }

        final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
        SecurityContext securityContext = new SecurityContext() {

            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        return username;
                    }
                };
            }

            @Override
            public boolean isUserInRole(String role) {
                return true;
            }

            @Override
            public boolean isSecure() {
                return currentSecurityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }

        };
        requestContext.setSecurityContext(securityContext);

        /*Get the authenticated user*/
        User authenticatedUser = retrieveAuthenticatedUser(username);

        /*Extract roles from current Ent-Point*/
        this.resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);
        this.resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);

        /*Check permissions for authenticated user*/
        String owner = null;
        String url = requestContext.getUriInfo().getAbsolutePath().toString();
        if (classRoles.contains(Role.owner) || methodRoles.contains(Role.owner)) {
            if (url.contains("users")) {                     //An xtyphses sto /users edw
                if (url.contains("comments")) {
                    String id = pathParameters.getFirst("comment_id");
                    Integer comment_id = Integer.valueOf(id);
                    owner = retrieveCommentOwner(comment_id);
                } else if (url.contains("deleteProfilePicture")) {
                    String id = pathParameters.getFirst("picture_id");
                    Integer picture_id = Integer.valueOf(id);
                    owner = retrievePictureOwner(picture_id);
                } else if (url.contains("answerMessage")) {
                    String my_username = pathParameters.getFirst("username");
                    String id = pathParameters.getFirst("message_id");
                    Integer message_id = Integer.valueOf(id);
                    owner = retrieveMessageOwner(message_id, authenticatedUser.getUsername());
                } else
                    owner = pathParameters.getFirst("username");
            } else if (url.contains("rooms")) {
                if (url.contains("cancelReservation"))
                    owner = retrieveReservationOwner(Integer.valueOf(pathParameters.getFirst("id")));
                else if (url.contains("mylistings") || url.contains("myreservations"))
                    owner = pathParameters.getFirst("username");
                else if (url.contains("reviews"))
                    owner = retrieveReviewOwner(Integer.valueOf(pathParameters.getFirst("review_id")));
                else
                    owner = retrieveRoomOwner(Integer.valueOf(pathParameters.getFirst("id")));
            }
        }

        if (methodRoles.isEmpty())
            checkPermissions(classRoles, authenticatedUser, owner);
        else
            checkPermissions(methodRoles, authenticatedUser, owner);
    }


}
