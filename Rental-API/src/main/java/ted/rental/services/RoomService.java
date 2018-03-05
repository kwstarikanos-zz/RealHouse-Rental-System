package ted.rental.services;

import ted.rental.database.DataAccessObject;
import ted.rental.database.entities.*;
import ted.rental.endpoints.queryParams.RoomEndpointFilterBean;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.*;
import ted.rental.model.inputs.CalendarEntry;
import ted.rental.model.inputs.ReservationInput;
import ted.rental.model.inputs.RoomInput;
import ted.rental.model.outputs.FilteredTuples;
import ted.rental.model.outputs.ResidenceObj;
import ted.rental.model.outputs.ResidencesObj;
import ted.rental.model.outputs.RoomObj;

import javax.ws.rs.BeanParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.text.ParseException;
import java.util.*;
import java.util.Calendar;
import java.util.Date;

public class RoomService {

    public RoomService() {

    }

    public ResidencesObj getAllRooms(@BeanParam RoomEndpointFilterBean filter, @Context UriInfo uriInfo) {
        DataAccessObject dao = new DataAccessObject();
        List<RoomEntity> entities = dao.getTuples("rooms.findAll");

        List<Room> rooms = new ArrayList<>();
        List<RoomObj> roomObjs = new ArrayList<>();
        ResidencesObj residencesObj = new ResidencesObj();

        for (RoomEntity entity : entities) {
            rooms.add(entity.toModel(filter.getDepth()));
        }

        for (Room room : rooms)
            roomObjs.add(room.toRoomObjs(uriInfo));
        residencesObj.setRooms(roomObjs);
        residencesObj.setCount(entities.size());
        return residencesObj;
    }

    public ResidencesObj getAllRoomsPaginated(@BeanParam RoomEndpointFilterBean filter, @Context UriInfo uriInfo) throws ParseException {
        DataAccessObject dao = new DataAccessObject();
        List<Room> rooms = new ArrayList<>();

        List<RoomEntity> entities = new ArrayList<>();

        ResidencesObj residencesObjs = new ResidencesObj();

        List<RoomObj> roomsObj = new ArrayList<>();


        java.sql.Date checkin = null;
        java.sql.Date checkout = null;

        if (filter.getCheckinDate() != null) {
            checkin = java.sql.Date.valueOf(filter.getCheckinDate());
        }

        if (filter.getCheckoutDate() != null) {
            checkout = java.sql.Date.valueOf(filter.getCheckoutDate());
        }

        int days = 1;
        long count = 0;
        /*Calculate the amount of dates between 2 sql dates*/
        if (filter.getCheckoutDate() != null && filter.getCheckinDate() != null) {
            java.util.Date d1 = checkin;
            java.util.Date d2 = checkout;
            while (d2.after(d1)) {
                Calendar c = Calendar.getInstance();
                c.setTime(d1);
                c.add(Calendar.DATE, 1);
                d1 = c.getTime();
                days++;
            }
            System.out.println("The amount of days is: " + days);
        }

        if ((filter.getLocality() != null || filter.getCountry() != null) && (filter.getCheckinDate() != null || filter.getCheckoutDate() != null)) {
            dao.setParam("guests", filter.getGuests());
            dao.setStart(filter.getStart());
            dao.setLimit(filter.getSize());
            System.out.println("You gave location, checkin/checkout dates and guests!");
            if ((filter.getCheckinDate() != null || filter.getCheckoutDate() != null) && filter.getLocality() != null && filter.getCountry() != null) {
                if (filter.getCheckinDate() != null && filter.getCheckoutDate() != null) {
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("locality", filter.getLocality());
                    dao.setParam("checkin", checkin);
                    dao.setParam("checkout", checkout);
                    dao.setParam("noOfDays", days);
                    entities = dao.getTuples("rooms.findByLocationAndBothDates");
                    dao.setParam("guests", filter.getGuests());
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("locality", filter.getLocality());
                    dao.setParam("checkin", checkin);
                    dao.setParam("checkout", checkout);
                    dao.setParam("noOfDays", days);
                    count = dao.getCount("rooms.findCountByLocationAndBothDates");
                } else if (filter.getCheckoutDate() == null) {
                    System.out.println("Checkin is null!");
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("locality", filter.getLocality());
                    dao.setParam("date", checkin);
                    entities = dao.getTuples("rooms.findByLocationAndSingleDate");
                    dao.setParam("guests", filter.getGuests());
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("locality", filter.getLocality());
                    dao.setParam("date", checkin);
                    count = dao.getCount("rooms.findCountByLocationAndSingleDate");
                } else {
                    System.out.println("Checkout is null!");
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("locality", filter.getLocality());
                    dao.setParam("date", checkout);
                    entities = dao.getTuples("rooms.findByLocationAndSingleDate");
                    dao.setParam("guests", filter.getGuests());
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("locality", filter.getLocality());
                    dao.setParam("date", checkout);
                    count = dao.getCount("rooms.findCountByLocationAndSingleDate");
                }
            } else if (filter.getLocality() == null && filter.getCountry() != null && (filter.getCheckinDate() != null || filter.getCheckoutDate() != null)) {
                if (filter.getCheckinDate() != null && filter.getCheckoutDate() != null) {
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("checkin", checkin);
                    dao.setParam("checkout", checkout);
                    dao.setParam("noOfDays", days);
                    entities = dao.getTuples("rooms.findByCountryAndBothDates");
                    dao.setParam("guests", filter.getGuests());
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("checkin", checkin);
                    dao.setParam("checkout", checkout);
                    dao.setParam("noOfDays", days);
                    count = dao.getCount("rooms.findCountByCountryAndBothDates");
                } else if (filter.getCheckoutDate() == null && filter.getCheckinDate() != null) {
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("date", checkin);
                    entities = dao.getTuples("rooms.findByCountryAndSingleDate");
                    dao.setParam("guests", filter.getGuests());
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("date", checkin);
                    count = dao.getCount("rooms.findCountByCountryAndSingleDate");
                } else {
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("date", checkout);
                    entities = dao.getTuples("rooms.findByCountryAndSingleDate");
                    dao.setParam("guests", filter.getGuests());
                    dao.setParam("country", filter.getCountry());
                    dao.setParam("date", checkout);
                    count = dao.getCount("rooms.findCountByCountryAndSingleDate");
                }
            }
        } else if ((filter.getLocality() == null && filter.getCountry() == null) && (filter.getCheckinDate() != null || filter.getCheckoutDate() != null)) {
            /*The answer to this solution is here: https://stackoverflow.com/questions/45041341/jpql-checkif-room-is-available-between-dates*/
            dao.setParam("guests", filter.getGuests());
            dao.setStart(filter.getStart());
            dao.setLimit(filter.getSize());
            if (filter.getCheckinDate() != null && filter.getCheckoutDate() != null) {
                System.out.println("Neither checkin nor checkout is null!");
                dao.setParam("checkin", checkin);
                dao.setParam("checkout", checkout);
                dao.setParam("noOfDays", days);
                entities = dao.getTuples("rooms.findByBothDates");
                dao.setParam("guests", filter.getGuests());
                dao.setParam("checkin", checkin);
                dao.setParam("checkout", checkout);
                dao.setParam("noOfDays", days);
                count = dao.getCount("rooms.findCountByBothDates");
            } else if (filter.getCheckinDate() != null && filter.getCheckoutDate() == null) {
                System.out.println("Checkout is null!");
                dao.setParam("date", checkin);
                entities = dao.getTuples("rooms.findBySingleDate");
                dao.setParam("guests", filter.getGuests());
                dao.setParam("date", checkin);
                count = dao.getCount("rooms.findCountBySingleDate");
            } else {
                System.out.println("Checkin is null!");
                dao.setParam("date", checkout);
                entities = dao.getTuples("rooms.checkSingleDate");
                dao.setParam("guests", filter.getGuests());
                dao.setParam("date", checkout);
                count = dao.getCount("rooms.findCountBySingleDate");
            }
        } else if ((filter.getLocality() != null || filter.getCountry() != null) && (filter.getCheckinDate() == null && filter.getCheckoutDate() == null)) {
            dao.setStart(filter.getStart());
            dao.setLimit(filter.getSize());
            dao.setParam("guests", filter.getGuests());
            if (filter.getCountry() != null && filter.getLocality() != null) {
                dao.setParam("country", filter.getCountry());
                dao.setParam("locality", filter.getLocality());
                entities = dao.getTuples("rooms.findByLocalityAndCountry");
                dao.setParam("guests", filter.getGuests());
                dao.setParam("country", filter.getCountry());
                dao.setParam("locality", filter.getLocality());
                count = dao.getCount("rooms.findCountByLocalityAndCountry");
            } else if (filter.getCountry() != null && filter.getLocality() == null) {
                dao.setParam("country", filter.getCountry());
                entities = dao.getTuples("rooms.findByCountry");
                dao.setParam("guests", filter.getGuests());
                dao.setParam("country", filter.getCountry());
                count = dao.getCount("rooms.findCountByCountry");
            }
        } else {
            dao.setStart(filter.getStart());
            dao.setLimit(filter.getSize());
            dao.setParam("guests", filter.getGuests());
            entities = dao.getTuples("rooms.findByNumberOfGuests");
            dao.setParam("guests", filter.getGuests());
            count = dao.getCount("rooms.findCountByNumberOfGuests");
        }

        for (RoomEntity entity : entities) {
            rooms.add(entity.toModel(filter.getDepth()));
        }


        for (Room room : rooms)
            roomsObj.add(room.toRoomObjs(uriInfo));
        residencesObjs.setRooms(roomsObj);
        residencesObjs.setCount(count);


        return residencesObjs;
    }

    public ResidencesObj getRoomFiltered(RoomEndpointFilterBean filter, @Context UriInfo uriInfo) throws ParseException {
        DataAccessObject dao = new DataAccessObject();
        final List<RoomEntity> roomEntities = new ArrayList<>();
        final List<Room> rooms = new ArrayList<>();
        final List<RoomObj> roomsObj = new ArrayList<>();
        final FilteredTuples ft = dao.getFilteredTuples(filter, "overnight_price", "ASC");
        final List<Object> typles = ft.getTyples();

        for (Object o : typles) {
            roomEntities.add((RoomEntity) o);
        }

        for (RoomEntity entity : roomEntities) {
            roomsObj.add(entity.toModel(filter.getDepth()).toRoomObjs(uriInfo));
        }

        return new ResidencesObj(roomsObj, ft.getCount(), ft.getMinPrice(), ft.getMaxPrice());
    }

    public List<Room> findRoomsByNumberOfGuests(Integer guests) {
        DataAccessObject dao = new DataAccessObject();
        List<Room> rooms = new ArrayList<>();
        dao.setParam("max_people", guests);
        List<RoomEntity> roomEntities = dao.getTuples("rooms.findRoomsByNumberOfGuests");
        for (RoomEntity o : roomEntities) {
            rooms.add(o.toModel(0));
        }
        return rooms;
    }

    public Room insertRoom(RoomInput room) {
        DataAccessObject dao = new DataAccessObject();

        dao.setParam("username", room.getOwner());
        List<HostEntity> owner = dao.getTuples("users.findByUsername");

        RoomEntity entity = room.toRoomEntity();
        entity.setLocationEntity(room.getLocation().toEntity());

        System.out.println(entity.toString());

        Collection<File> pictures = room.getPictures();

        for (File file : room.getPictures()) {

            System.out.println(file.toString() + "\n\n\n");

            FileEntity fileEntity = dao.insertTuple(file.toEntity());
            if(fileEntity == null)
                throw new ErrorException(Response.Status.NOT_ACCEPTABLE, "File '" + file.getFilename() + "' is too large! Remove this file and try again...");

            PictureEntity pictureEntity = new PictureEntity(fileEntity, fileEntity, fileEntity); /*TODO: <= Resize plugin*/
            entity.getPictureEntities().add(pictureEntity);
        }

        for (Amenities amenity : room.getAmenities())
            entity.getAmenitiesEntities().add(amenity.toEntity());

        for (Rule rule : room.getRules())
            entity.getRuleEntities().add(rule.toEntity());

        for (Transport transport : room.getTransports())
            entity.getTransportEntities().add(transport.toEntity());

        entity.setHostEntity(owner.get(0));

        entity.setTypeEntity(room.getType().toEntity());

        entity = dao.insertTuple(entity);
        if(entity != null){
            owner.get(0).getRoomEntities().add(entity);
            dao.updateTuple(owner.get(0));
            return entity.toModel(0);
        }
        else
            return null;
    }

    public List<Room> getRoomsByOwner(String owner) {
        DataAccessObject dao = new DataAccessObject();
        List<Room> rooms = new ArrayList<>();
        dao.setParam("username", owner);
        List<RoomEntity> entities = dao.getTuples("rooms.findRoomsByOwner");
        for (RoomEntity entity : entities) {
            rooms.add(entity.toModel(1));
        }
        return rooms;
    }

    public ResidenceObj getRoomById(Integer id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", id);
        List<RoomEntity> roomEntities = dao.getTuples("rooms.findById");
        dao.setParam("id", id);
        dao.setStart(0);
        dao.setLimit(10);
        if (!roomEntities.isEmpty()) {
            List<ReviewEntity> reviewEntities = dao.getTuples("reviews.findByIdOrderByDate");
            roomEntities.get(0).setReviewEntities(reviewEntities);
        }
        return roomEntities.isEmpty() ? null : roomEntities.get(0).toResidenceObj();
    }

    //TODO
    public Boolean checkRoomAvailability(Integer room_id, Date arrival_date, Date departure_date) {
        DataAccessObject dao = new DataAccessObject();
        List<CalendarEntity> calendarEntities;
        Date dt = arrival_date;
        if (arrival_date != null && departure_date == null) {
            dao.setParam("date", utilDateToSqlDate(arrival_date));
            dao.setParam("room_id", room_id);
            calendarEntities = dao.getTuples("calendar.findByDateAndRoomId");
            if (calendarEntities.isEmpty()) {
                return false;
            } else {
                return calendarEntities.get(0).getAvailable();
            }

        } else if (arrival_date == null && departure_date != null) {
            dao.setParam("date", utilDateToSqlDate(departure_date));
            dao.setParam("room_id", room_id);
            calendarEntities = dao.getTuples("calendar.findByDateAndRoomId");
            if (calendarEntities.isEmpty()) {
                return false;
            } else {
                return calendarEntities.get(0).getAvailable();
            }

        } else if (arrival_date != null) {
            dao.setParam("date", utilDateToSqlDate(dt));
            dao.setParam("room_id", room_id);
            calendarEntities = dao.getTuples("calendar.findByDateAndRoomId");
            if (calendarEntities.isEmpty()) {
                return false;
            } else {
                if (!calendarEntities.get(0).getAvailable()) {
                    return false;
                }
            }
            dao.resetParams();
            while (departure_date.after(dt)) {
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DATE, 1);
                dt = c.getTime();
                dao.setParam("room_id", room_id);
                dao.setParam("date", utilDateToSqlDate(dt));
                calendarEntities = dao.getTuples("calendar.findByDateAndRoomId");
                if (calendarEntities.isEmpty()) {
                    return false;
                } else {
                    if (!calendarEntities.get(0).getAvailable()) {
                        return false;
                    }
                }
                dao.resetParams();
            }
        }

        return true;
    }

    public File getRoomPicture(Integer room_id, Integer picture_id, String size) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("room_id", room_id);
        dao.setParam("picture_id", picture_id);
        List<ROOMENTITY_PICTUREENTITY> roomentity_pictureentities = dao.getTuples("roompicture.findByIds");
        if (roomentity_pictureentities.isEmpty())
            throw new ErrorException(Response.Status.NOT_FOUND, "The picture number " + picture_id + " does not belong to room number " + room_id);
        File file = null;
        if (size != null) {
            if (size.equals("thumbnail")) {
                file = roomentity_pictureentities.get(0).getPictureEntity().getThumbnail() == null ? null : roomentity_pictureentities.get(0).getPictureEntity().getThumbnail().toModel();
            } else if (size.equals("medium")) {
                file = roomentity_pictureentities.get(0).getPictureEntity().getMedium() == null ? null : roomentity_pictureentities.get(0).getPictureEntity().getMedium().toModel();
            } else if (size.equals("original")) {
                file = roomentity_pictureentities.get(0).getPictureEntity().getPicture() == null ? null : roomentity_pictureentities.get(0).getPictureEntity().getPicture().toModel();
            }
        } else {
            file = roomentity_pictureentities.get(0).getPictureEntity().getPicture() == null ? null : roomentity_pictureentities.get(0).getPictureEntity().getPicture().toModel();
        }

        if (file == null)
            throw new ErrorException(Response.Status.NOT_FOUND, "The picture does not exist on that size!");
        return file;
    }

    public void setCalendarEntries(CalendarEntry a, Integer room_id) {
        DataAccessObject dao = new DataAccessObject();
        Date dt = a.getAvailable_from();
        CalendarEntity calendarEntity;
        dao.setParam("id", room_id);
        List<RoomEntity> roomEntities = dao.getTuples("rooms.findById");
        dao.resetParams();
        calendarEntity = new CalendarEntity(room_id, utilDateToSqlDate(dt), true,
                a.getPrice(), roomEntities.get(0));
        dao.updateTuple(calendarEntity);
        while (a.getAvailable_to().after(dt)) {
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();
            calendarEntity = new CalendarEntity(room_id, utilDateToSqlDate(dt), true,
                    a.getPrice(), roomEntities.get(0));
            dao.updateTuple(calendarEntity);
        }
    }

    private java.sql.Date utilDateToSqlDate(Date dt) {
        java.util.Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
        return sqlDate;
    }

    public void deleteCalendarEntries(Date begin, Date end, Integer room_id) {
        DataAccessObject dao = new DataAccessObject();
        Date dt = begin;
        dao.setParam("room_id", room_id);
        dao.setParam("date", utilDateToSqlDate(dt));
        dao.removeTuple("calendar.deleteByDateAndRoomId");
        while (end.after(dt)) {
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();
            dao.setParam("room_id", room_id);
            dao.setParam("date", utilDateToSqlDate(dt));
            dao.removeTuple("calendar.deleteByDateAndRoomId");

        }
    }

    public void bookRoom(Integer room_id, String username, ReservationInput reservation) {
        DataAccessObject dao = new DataAccessObject();
        List<RoomEntity> roomEntities;
        List<RenterEntity> renterEntities;
        Date dt = reservation.getCheckInDate();
        List<CalendarEntity> calendarEntities;

        if (!checkRoomAvailability(room_id, reservation.getCheckInDate(), reservation.getCheckOutDate())) {
            throw new ErrorException(Response.Status.CONFLICT, "The room is not available on that day!");
        }

        dao.setParam("id", room_id);
        roomEntities = dao.getTuples("rooms.findById");
        dao.resetParams();
        if (roomEntities.get(0).getMax_people() < reservation.getGuests()) {
            throw new ErrorException(Response.Status.CONFLICT, "The room can't contain that amount of guests!");
        }
        dao.setParam("username", username);
        renterEntities = dao.getTuples("renter.findByUsername");
        ReservationEntity reservationEntity = reservation.toEntity(roomEntities.get(0), renterEntities.get(0));
        dao.insertTuple(reservationEntity);
        dao.resetParams();
        dao.setParam("room_id", room_id);
        dao.setParam("date", utilDateToSqlDate(dt));
        calendarEntities = dao.getTuples("calendar.findByDateAndRoomId");
        calendarEntities.get(0).setRenterEntity(renterEntities.get(0));
        calendarEntities.get(0).setAvailable(false);
        dao.updateTuple(calendarEntities.get(0));
        dao.resetParams();

        while (reservation.getCheckOutDate().after(dt)) {
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();
            dao.setParam("room_id", room_id);
            dao.setParam("date", utilDateToSqlDate(dt));
            calendarEntities = dao.getTuples("calendar.findByDateAndRoomId");
            calendarEntities.get(0).setRenterEntity(renterEntities.get(0));
            calendarEntities.get(0).setAvailable(false);
            dao.updateTuple(calendarEntities.get(0));
            dao.resetParams();
        }
    }

    public String retrieveRoomOwner(Integer room_id) {
        DataAccessObject dao = new DataAccessObject();
        System.out.print("Id is: " + room_id);
        dao.setParam("id", room_id);
        List<RoomEntity> entities = dao.getTuples("rooms.findById");
        return entities.isEmpty() ? null : entities.get(0).getHostEntity().getUsername();
    }

    public String retrieveReservationOwner(Integer reservation_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", reservation_id);
        List<ReservationEntity> entities = dao.getTuples("reservation.findById");
        return entities.isEmpty() ? null : entities.get(0).getRenterEntity().getUsername();
    }

    public void updateRoom(Integer room_id, Room room) {
        DataAccessObject dao = new DataAccessObject();
        RoomEntity roomEntity = room.toEntity();
        roomEntity.setId(room_id);
        dao.updateTuple(roomEntity); //TODO na ginei dokimh
    }

    public boolean insertRoomPictures(Integer room_id, List<File> files) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", room_id);
        List<RoomEntity> entities = dao.getTuples("rooms.findById");
        for (File file : files) {
            FileEntity fileEntity = dao.insertTuple(file.toEntity());
            if(fileEntity == null)
                throw new ErrorException(Response.Status.NOT_ACCEPTABLE, "File '" + file.getFilename() + "' is too large!");

            PictureEntity pictureEntity = new PictureEntity(fileEntity, fileEntity, fileEntity); /*TODO: <= Resize plugin*/
            entities.get(0).getPictureEntities().add(pictureEntity);
        }
        return dao.updateTuple(entities.get(0)) != null;
    }

    public void deleteRoom(Integer room_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", room_id);
        dao.removeTuple("rooms.deleteById");
    }

    public void deleteRoomPictures(Integer picture_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", picture_id);
        dao.removeTuple("pictures.deleteById");

    }

    public void cancelReservation(Integer reservation_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", reservation_id);
        List<ReservationEntity> reservationEntities = dao.getTuples("reservation.findById");
        dao.resetParams();
        ReservationEntity reservation = reservationEntities.get(0);
        Date dt = reservation.getCheckInDate();
        dao.setParam("room_id", reservation.getRoomEntity().getId());
        dao.setParam("date", utilDateToSqlDate(dt));
        List<CalendarEntity> calendarEntities = dao.getTuples("calendar.findByDateAndRoomId");
        calendarEntities.get(0).setAvailable(true);
        calendarEntities.get(0).setRenterEntity(null);
        dao.updateTuple(calendarEntities.get(0));
        dao.resetParams();
        while (reservation.getCheckOutDate().after(dt)) {
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();
            dao.setParam("room_id", reservation.getRoomEntity().getId());
            dao.setParam("date", utilDateToSqlDate(dt));
            calendarEntities = dao.getTuples("calendar.findByDateAndRoomId");
            calendarEntities.get(0).setAvailable(true);
            calendarEntities.get(0).setRenterEntity(null);
            dao.updateTuple(calendarEntities.get(0));
            dao.resetParams();
        }
        dao.setParam("id", reservation_id);
        dao.removeTuple("reservation.deleteById");
    }

    public List<Reservation> getMyReservations(String owner) {
        DataAccessObject dao = new DataAccessObject();
        List<Reservation> reservations = new ArrayList<>();
        dao.setParam("owner", owner);
        List<ReservationEntity> reservationEntities = dao.getTuples("reservation.findByOwner");
        for (ReservationEntity reservationEntity : reservationEntities) {
            reservations.add(reservationEntity.toModel());
        }
        return reservations;
    }

    public Integer approveReservation(Integer reservation_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", reservation_id);
        List<ReservationEntity> reservationEntities = dao.getTuples("reservation.findById");
        if (reservationEntities.isEmpty()) {
            throw new ErrorException(Response.Status.NOT_FOUND, "The reservation you are trying to access does not exist!");
        }
        ReservationEntity reservationEntity = reservationEntities.get(0);
        reservationEntity.setApproved(true);
        dao.updateTuple(reservationEntities.get(0));
        BookingEntity bookingEntity = new BookingEntity(reservationEntity.getCheckInDate(),
                reservationEntity.getCheckOutDate(), reservationEntity);
        BookingEntity bookingEntity1 = dao.insertTuple(bookingEntity);
        return bookingEntity1.getId();

    }

}
