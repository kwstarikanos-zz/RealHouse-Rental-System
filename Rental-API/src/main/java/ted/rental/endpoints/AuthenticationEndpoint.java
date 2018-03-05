package ted.rental.endpoints;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.ws.rs.core.SecurityContext;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import ted.rental.annotations.Role;
import ted.rental.annotations.Secured;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.inputs.Credentials;
import ted.rental.model.User;
import ted.rental.model.inputs.Register;
import ted.rental.model.outputs.*;
import ted.rental.services.SessionService;
import ted.rental.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.sql.Timestamp;
import java.util.Date;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static ted.rental.annotations.Role.*;
import static ted.rental.config.Constraint.*;

@Path("/authentication")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationEndpoint {

    private static UserService userService;
    private static SessionService session;

/*    @Inject
    private UserService userService;*/

    /*OK -- Access: private*/
    public AuthenticationEndpoint() {
        userService = new UserService();
        session = new SessionService();
    }

    /*OK -- Access: private*/
    private String issueToken(final String username, final Role role, Date issuedAt, Date expiration, Boolean confirmed) throws Exception {
        Key key = ted.rental.utilities.KeyHolder.key;

        /*TODO: CONFIRMED <-------------------------*/

        return Jwts.builder()
                .claim("role", role)
                .claim("confirmed", confirmed)
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .signWith(SignatureAlgorithm.HS512, key)
                .setExpiration(expiration)
                .compact();
    }

    /*OK -- Access: everyone*/
    @POST
    @Path("/login")
    public AccessObj authenticateUser(@Valid @NotNull final Credentials credentials, @Context HttpServletRequest request) {
        try {
            String username = credentials.getUsername();
            String password = credentials.getPassword();
            User authenticatedUser = userService.findByUsernameAndPassword(username, password);
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            long expMillis = nowMillis + 3600000L;
            //long expMillis = nowMillis + 5000L;
            Date expiration = new Date(expMillis);
            String accessToken = null;
            accessToken = issueToken(authenticatedUser.getUsername(), authenticatedUser.getRole(), now, expiration, authenticatedUser.getConfirmed());
            String clientIp = request.getRemoteAddr();
            session.create(username, accessToken, clientIp, new Timestamp(nowMillis), new Timestamp(expMillis));
            return new AccessObj(accessToken);
        } catch (Exception e) {
            throw new ErrorException(UNAUTHORIZED, "Invalid credentials!");
        }
    }

    /*OK -- Access: ValidTokenUsers*/
    @Secured(roles = {admin, renter, host, owner})
    @GET
    @Path("/logout")
    public Response logout(@Context SecurityContext securityContext) {
        try {
            String username = ((securityContext != null) ? securityContext.getUserPrincipal().getName() : "null");
            if (session.revoke(username) > 0)
                return Response.ok().build();
            else
                return Response.noContent().build();
        } catch (Exception e) {
            throw new ErrorException(INTERNAL_SERVER_ERROR, "Oops, Something went wrong!");
        }
    }

    /*-- Access: everyone*/
    @POST
    @Path("/register")
    public Response registerUser(@Valid @NotNull final Register register, @Context UriInfo uriInfo)
            throws URISyntaxException {
        User newUser = userService.addUser(register);
        URI uri = uriInfo.getBaseUriBuilder().path(UserEndpoint.class).path(newUser.getUsername()).build();
        return Response.created(uri).build();
    }

    /*OK -- Access: everyone*/
    @GET
    @Path("check/username/{username}")
    public UsernameObj checkUsername(
            @PathParam("username")
            @NotNull
            @Length(min = USERNAME_MIN, max = USERNAME_MAX)
            @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_MESSAGE) final String username) {
        try {
            return new UsernameObj(userService.getUserByUsername(username) == null);
        } catch (Exception e) {
            throw new ErrorException(INTERNAL_SERVER_ERROR, "Oops, something went wrong");
        }
    }

    /*OK -- Access: ValidToken & loggedIn Users*/
    @Secured(roles = {admin, renter, host, owner})
    @GET
    @Path("check")
    public Response authenticated() {
        return Response.ok().build();
    }

    /*OK -- Access: everyone*/
    @GET
    @Path("check/email/{email}")
    public EmailObj checkEmail(
            @PathParam("email")
            @NotNull
            @Email final String email) {
        try {
            return new EmailObj(userService.getUserByEmail(email) == null);
        } catch (Exception e) {
            throw new ErrorException(INTERNAL_SERVER_ERROR, "Oops, something went wrong");
        }
    }

    /*OK -- Access: everyone*/
    @GET
    @Path("check/phone/{phone}")
    public PhoneObj checkPhone(
            @PathParam("phone")
            @NotNull
            @Length(min = PHONE_MIN, max = PHONE_MAX)
            @Pattern(regexp = PHONE_PATTERN) final String phone) {
        try {
            return new PhoneObj(userService.getUserByPhone(phone)== null);
        } catch (Exception e) {
            throw new ErrorException(INTERNAL_SERVER_ERROR, "Oops, something went wrong");
        }
    }
}
