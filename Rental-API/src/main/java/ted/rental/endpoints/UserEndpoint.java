package ted.rental.endpoints;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.security.SecurityContext;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import ted.rental.annotations.Secured;
import ted.rental.annotations.AuthenticatedUser;
import ted.rental.database.entities.CommentEntity;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.*;
import ted.rental.endpoints.queryParams.UserResourceFilterBean;
import ted.rental.model.outputs.InboxMessage;
import ted.rental.model.outputs.OutboxMessage;
import ted.rental.services.CommentService;
import ted.rental.services.MessageService;
import ted.rental.services.ProfileService;
import ted.rental.services.UserService;

import static javax.ws.rs.core.Response.Status.*;
import static ted.rental.annotations.Role.*;
import static ted.rental.config.Constraint.*;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserEndpoint {

    private static UserService userService;
    private static ProfileService profileService;
    private static CommentService commentService;
    private static MessageService messageService;

    @Autowired
    @AuthenticatedUser
    private User authenticatedUser;

    @Context
    SecurityContext securityContext;

    /*Constructor*/
    public UserEndpoint() {
        userService = new UserService();
        profileService = new ProfileService();
        commentService = new CommentService();
        messageService = new MessageService();
    }

    /*Access: admin*/
    @Secured(roles = {admin})
    @GET
    public @Valid
    List<User> getUsers(@BeanParam UserResourceFilterBean filter) {
        if (filter.getStart() >= 0 && filter.getSize() >= 0)
            return userService.getAllUsersPaginated(filter.getStart(), filter.getSize(), filter.getDepth());
        return userService.getAllUsers(filter.getDepth());
    }

    @Secured(roles = admin)
    @GET
    @Path("/{role}/show")
    public List<User> getUsersByRole(@PathParam("role") String role) {
        return userService.getUsersByRole(role);
    }

    /*Access: admin, owner*/
    @GET
    @Path("/{username}")
    @Secured(roles = {admin, owner})
    public @Valid
    User getUser(
            @PathParam("username")
            @NotNull
            @Length(min = USERNAME_MIN, max = USERNAME_MAX)
            @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_MESSAGE) String username, @Context SecurityContext securityContext) {
        if (authenticatedUser != null)
            System.out.println("Authenticated user = " + authenticatedUser.toString());

        /*TODO: Authenticated User Instance <-------------------------------------------------------------*/
        /*
        Principal principal = securityContext.getUserPrincipal();
        String username1 = principal.getName();
        System.out.println("\nUserResource::getUser : Authenticated USER:" + username1);
        */
        return userService.getUserByUsername(username);
    }

    /*Access: everyone*/
    @GET
    @Path("/{username}/profile")
    public Profile getProfile(@PathParam("username") String owner, @Context UriInfo uriInfo) {
        Profile profile = profileService.getProfile(owner);
        if (profile == null)
            throw new ErrorException(Response.Status.NOT_FOUND, "The profile you are trying to access does not exist!");
        return profile;
    }

    @GET
    @Path("/{username}/profile/picture")
    public Response getProfilePicture(@PathParam("username") String username){
        File file = profileService.getProfilePicture(username);
        return Response.status(Response.Status.OK).entity(file).build();
    }

    /*Access: admin*/
    @POST
    @Secured(roles = {admin})
    public Response createUser(User user, @Context UriInfo uriInfo
    ) throws URISyntaxException {
        try {
            User newUser = userService.addUser(user);
            URI uri = uriInfo.getAbsolutePathBuilder().path(newUser.getUsername()).build();
            return Response.created(uri).entity(newUser).build();
        } catch (Exception e) {
            throw new ErrorException(CONFLICT, "Failed to add the user successfully, probably the user already exists! ERROR: " + e.getMessage());
        }
    }

    /*Access: admin, owner*/
    @PUT
    @Secured(roles = {admin, owner})
    @Path("/{username}")
    public Response updateUser(
            @PathParam("username")
            @NotNull
            @Length(min = USERNAME_MIN, max = USERNAME_MAX)
            @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_MESSAGE)
                    String username, @Valid @NotNull User user) {
        userService.updateUser(user, username);
        return Response.status(ACCEPTED).build();
    }

    @PUT
    @Secured(roles = {admin})
    @Path("/{username}/confirm")
    public Response confirmUser(@PathParam("username") String username) {
        userService.confirmUserByUsername(username);
        return Response.status(ACCEPTED).build();
    }

    @POST
    @Secured(roles = {owner})
    @Path("/{username}/profile/picture")
    public Response insertProfilePicture(@PathParam("username") String username, File file) {
        profileService.updateProfilePicture(username, file);
        return Response.status(ACCEPTED).build();
    }


    @PUT
    @Secured(roles = {owner})
    @Path("/{username}/profile")
    public Response updateProfile(@PathParam("username") String username, Profile profile) {
        profileService.updateProfile(username, profile);
        return Response.status(ACCEPTED).build();
    }

    /*Access: admin, owner*/
    @DELETE
    @Secured(roles = {admin, owner})
    @Path("/{username}")
    public Response deleteUser(
            @PathParam("username")
            @NotNull
            @Length(min = USERNAME_MIN, max = USERNAME_MAX)
            @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_MESSAGE)
                    String username) {
        if (userService.deleteUser(username))
            return Response.status(Response.Status.ACCEPTED).build();
        else
            throw new ErrorException(Response.Status.NOT_ACCEPTABLE, "No user deletion was made!");
    }


    @DELETE
    @Secured(roles = {admin, owner})
    @Path("/{username}/profile/picture/{picture_id}")
    public Response deleteProfilePicture(@PathParam("username") String username ,@PathParam("picture_id") Integer picture_id){
        profileService.deleteProfilePicture(username, picture_id);
        return Response.status(ACCEPTED).build();
    }



         /*                                                                   */
        /*                                                                   */
       /*                         Comments                                  */
      /*                                                                   */
     /*                                                                   */
    @GET
    @Path("/{username}/comments/")
    public Response getComments(@PathParam("username") String username) {
        List<Comment> comments = commentService.getComments(username);
        return Response.status(Response.Status.OK).entity(comments).build();
    }

    @POST
    @Path("/{username}/comments/")
    @Secured(roles = {renter, host})
    public Response postComment(Comment comment, @PathParam("username") String username, @Context UriInfo uriInfo) {
        CommentEntity newComment = commentService.postComment(comment, username);
        URI uri = uriInfo.getBaseUriBuilder().path(RoomEndpoint.class).path(newComment.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{username}/comments/{comment_id}")
    @Secured(roles = {admin, owner})
    public Response editComment(Comment comment, @PathParam("username") String username, @PathParam("comment_id") Integer comment_id) {
        commentService.editComment(comment, username, comment_id);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/{username}/comments/{comment_id}")
    @Secured(roles = {admin, owner})
    public Response deleteComment(@PathParam("comment_id") Integer comment_id) {
        commentService.deleteComment(comment_id);
        return Response.status(Response.Status.ACCEPTED).build();
    }


        /*                                                                   */
       /*                         Messages                                  */
      /*                                                                   */
     /*                                                                   */
    @GET
    @Secured(roles = owner)
    @Path("/{username}/messages/inbox")
    public Response getInbox(@PathParam("username") String username) {
        List<InboxMessage> messages = messageService.getInbox(username);
        return Response.status(Response.Status.OK).entity(messages).build();
    }

    @GET
    @Secured(roles = owner)
    @Path("/{username}/messages/outbox")
    public Response getOutbox(@PathParam("username") String username) {
        List<OutboxMessage> messages = messageService.getOutbox(username);
        return Response.status(Response.Status.OK).entity(messages).build();
    }

    @GET
    @Secured(roles = owner)
    @Path("/{username}/messages/deletedMessages")
    public Response getDeletedMessages(@PathParam("username") String username) {
        List<Message> messages = messageService.getDeletedMessages(username);
        return Response.status(Response.Status.OK).entity(messages).build();
    }

    @GET
    @Secured(roles = owner)
    @Path("/{username}/messages/{id}")
    public Message getMessage(@PathParam("id") Integer id) {
        Message message = messageService.getMessage(id);
        if (message == null)
            throw new ErrorException(Response.Status.NOT_FOUND, "The message you are trying to access does not exist!");
        return message;
    }

    @POST
    @Secured(roles = {renter, host})
    @Path("/{username}/messages/startConversation")
    public Response startConversation(@PathParam("username") String username, Message message){
        messageService.startConversation(username, message);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Secured(roles = owner)
    @Path("/{username}/messages/{message_id}/answerMessage")
    public Response answerMessage(@PathParam("username") String username, @PathParam("message_id") Integer message_id, Message message, @Context UriInfo uriInfo) {
        Integer id = messageService.answerMessage(username, message, message_id);
        URI uri = uriInfo.getBaseUriBuilder().path(RoomEndpoint.class).path(id.toString()).build();
        return Response.created(uri).build();
    }

    @DELETE
    @Secured(roles = owner)
    @Path("/{username}/messages/{id}")
    public Response deleteMessage(@PathParam("id") Integer id) {
        messageService.deleteMessage(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }

}
