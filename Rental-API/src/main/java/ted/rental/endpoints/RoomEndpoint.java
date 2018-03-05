package ted.rental.endpoints;


import com.sun.org.apache.regexp.internal.RE;
import org.apache.cxf.security.SecurityContext;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import ted.rental.annotations.Role;
import ted.rental.annotations.Secured;
import ted.rental.database.entities.ReviewEntity;
import ted.rental.endpoints.queryParams.RoomEndpointFilterBean;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.*;
import ted.rental.model.inputs.*;
import ted.rental.model.outputs.ResidenceObj;
import ted.rental.model.outputs.ResidencesObj;
import ted.rental.model.outputs.RoomObj;
import ted.rental.services.ReviewService;
import ted.rental.services.RoomService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import static ted.rental.annotations.Role.*;


@Path("/rooms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoomEndpoint {

    private static RoomService roomService;
    private static ReviewService reviewService;

    @Context
    SecurityContext securityContext;

    public RoomEndpoint() {
        roomService = new RoomService();
        reviewService = new ReviewService();
    }

    @GET
    public Response getRoomFiltered(@BeanParam RoomEndpointFilterBean filter, @Context UriInfo uriInfo) throws ParseException {
        return Response.status(Response.Status.OK).entity(roomService.getRoomFiltered(filter, uriInfo)).build();
    }

    @GET
    @Path("/{id}")
    public ResidenceObj getRoom(@PathParam("id") Integer id) {
        ResidenceObj residenceObj = roomService.getRoomById(id);

        if (residenceObj == null)
            throw new ErrorException(Response.Status.NOT_FOUND, "The room you are trying to access does not exist!");

        return residenceObj;
    }


    @GET
    @Secured(roles = {owner})
    @Path("/mylistings/{username}/")
    public Response getRoomsByOwner(@PathParam("username") String username) {
        List<Room> rooms = roomService.getRoomsByOwner(username);
        return Response.status(Response.Status.OK).entity(rooms).build();
    }


    @GET
    @Secured(roles = {owner})
    @Path("/myreservations/{username}")
    public Response getMyReservations(@PathParam("username") String username) {
        List<Reservation> reservations = roomService.getMyReservations(username);
        return Response.status(Response.Status.OK).entity(reservations).build();
    }

    @GET
    @Path("/{id}/checkavilability")
    public Response checkRoomAvailability(@PathParam("id") Integer room_id, Date arrival, Date departure) {
        Boolean availability;
        availability = roomService.checkRoomAvailability(room_id, arrival, departure);
        return Response.status(Response.Status.OK).entity(availability).build();
    }

    @GET
    @Path("/{id}/picture/{picture_id}")
    public Response getRoomPicture(@PathParam("id") Integer room_id, @PathParam("picture_id") Integer picture_id, @QueryParam("size") String size) {
        File file = roomService.getRoomPicture(room_id, picture_id, size);
        return Response.status(Response.Status.OK).entity(file).build();
    }

    @POST
    @Secured(roles = Role.host)
    public Response insertRoom(RoomInput room, @Context UriInfo uriInfo) {
        Room newRoom = roomService.insertRoom(room);
        URI uri = uriInfo.getBaseUriBuilder().path(RoomEndpoint.class).path(newRoom.getId().toString()).build();
        return Response.created(uri).build();
    }

    @POST
    @Secured(roles = {Role.renter, Role.host})
    @Path("/{id}/book/{username}")
    public Response bookRoom(@PathParam("id") Integer room_id, @PathParam("username") String username,
                             ReservationInput reservationInput) {
        roomService.bookRoom(room_id, username, reservationInput);
        return Response.status(Response.Status.CREATED).build();
    }


    @POST
    @Secured(roles = owner)
    @Path("/{id}/setCalendarEntries")
    public Response setCalendarEntries(@PathParam("id") Integer room_id, List<CalendarEntry> calendarEntries) {
        if (calendarEntries == null)
            throw new ErrorException(Response.Status.BAD_REQUEST, "You must provide an availability list!");
        for (CalendarEntry o : calendarEntries)
            roomService.setCalendarEntries(o, room_id);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Secured(roles = owner)
    @Path("/{id}")
    public Response updateRoom(@PathParam("id") Integer room_id, Room room) {
        roomService.updateRoom(room_id, room);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Secured(roles = owner)
    @Path("/{id}/picture")
    public Response insertRoomPictures(@PathParam("id") Integer room_id, List<File> files) {
        if(roomService.insertRoomPictures(room_id, files))
            return Response.status(Response.Status.ACCEPTED).build();
        else
            throw new ErrorException(Response.Status.NOT_ACCEPTABLE, "Picture isn't acceptable!");
    }

    @PUT
    @Secured(roles = {owner})
    @Path("/{id}/approveReservation/{reservation_id}")
    public Response approveReservation(@PathParam("reservation_id") Integer reservation_id, @Context UriInfo uriInfo) {
        Integer booking_id = roomService.approveReservation(reservation_id);
        URI uri = uriInfo.getBaseUriBuilder().path(RoomEndpoint.class).path(booking_id.toString()).build();
        return Response.status(Response.Status.ACCEPTED).entity(uri).build();
    }

    @DELETE
    @Secured(roles = owner)
    @Path("/{id}/deleteCalendarEntries")
    public Response deleteCalendarEntries(@PathParam("id") Integer room_id, CalendarDeletions calendarDeletions) {
        roomService.deleteCalendarEntries(calendarDeletions.getBegin(), calendarDeletions.getEnd(), room_id);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    @Secured(roles = owner)
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") Integer room_id) {
        roomService.deleteRoom(room_id);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    @Secured(roles = owner)
    @Path("/{id}/picture/{picture_id}")
    public Response deleteRoomPicture(@PathParam("picture_id") Integer picture_id) {
        roomService.deleteRoomPictures(picture_id);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    @Secured(roles = owner)
    @Path("/cancelReservation/{reservation_id}")
    public Response cancelReservation(@PathParam("reservation_id") Integer reservation_id) {
        roomService.cancelReservation(reservation_id);
        return Response.status(Response.Status.ACCEPTED).build();
    }





         /*                                                                   */
        /*                                                                   */
       /*                         Reviews                                   */
      /*                                                                   */
     /*                                                                   */

    @Path("/{id}/reviews/")
    @GET
    public Response getReviews(@PathParam("id") Integer id) {
        List<Review> reviews = reviewService.getReviews(id);
        return Response.status(Response.Status.OK).entity(reviews).build();
    }

    @Path("/{id}/reviews/")
    @Secured(roles = {renter, host})
    @POST
    public Response postReview(Review review, @PathParam("id") Integer id, @Context UriInfo uriInfo) {
        ReviewEntity reviewEntity = reviewService.postReview(review, id);
        URI uri = uriInfo.getBaseUriBuilder().path(RoomEndpoint.class).path(reviewEntity.getId().toString()).build();
        return Response.created(uri).build();
    }

    @Path("/{id}/reviews/{review_id}")
    @Secured(roles = {admin, owner})
    @PUT
    public Response editReview(Review review, @PathParam("id") Integer id, @PathParam("review_id") Integer review_id) {
        reviewService.editReview(review, review_id);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @Path("/{id}/reviews/{review_id}")
    @Secured(roles = {admin, owner})
    @DELETE
    public Response deleteReview(@PathParam("review_id") Integer review_id) {
        reviewService.deleteReview(review_id);
        return Response.status(Response.Status.ACCEPTED).build();
    }


}
