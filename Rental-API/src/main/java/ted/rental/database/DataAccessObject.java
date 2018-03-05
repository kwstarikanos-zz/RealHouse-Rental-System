package ted.rental.database;

import org.apache.cxf.common.util.StringUtils;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.jpa.JpaQuery;
import ted.rental.database.entities.*;
import ted.rental.endpoints.queryParams.RoomEndpointFilterBean;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.outputs.FilteredTuples;

import java.util.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.ws.rs.core.Response;

public class DataAccessObject {
    /*Default Persistence Unit Name*/
    private static final String unit = "PersistenceUnit";

    private final static EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");
    private EntityManager manager;
    private EntityTransaction transaction;
    private Query query;
    private Map<String, Object> params;
    private int start;
    private int limit;

    public DataAccessObject() {
        this(unit);
    }

    public DataAccessObject(final String unit) {
        params = new HashMap<>();
        start = 0;
        limit = 0;
    }

    public void resetParams() {
        params.clear();
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void setParam(final String key, final Object value) {
        params.put(key, value);
    }

    public void setStart(final int start) {
        this.start = start;
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }

    public <T> List<T> getTuples(final String namedQuery) {
        List<T> result = new ArrayList<>();
        manager = factory.createEntityManager();

        transaction = manager.getTransaction();
        transaction.begin();
        try {
            query = manager.createNamedQuery(namedQuery);
            if (!params.isEmpty())
                for (Map.Entry<String, Object> param : params.entrySet())
                    query.setParameter(param.getKey(), param.getValue());
            query.setFirstResult(start);
            if (limit > 0)
                query.setMaxResults(limit);
            result = query.getResultList();
            transaction.commit();

        } catch (DatabaseException d) {

            throw new ErrorException(Response.Status.INTERNAL_SERVER_ERROR, d.getMessage());


        } catch (PersistenceException e) {

        } finally {
            start = 0;
            limit = 0;
            params = new HashMap<>();

            if (manager.isOpen()) {
                manager.close();
            }
            //factory.close();
        }
        return result;
    }

    public Long getCount(final String namedQuery) {
        long count = 0;
        manager = factory.createEntityManager();
        transaction = manager.getTransaction();
        transaction.begin();
        try {
            query = manager.createNamedQuery(namedQuery);
            if (!params.isEmpty())
                for (Map.Entry<String, Object> param : params.entrySet())
                    query.setParameter(param.getKey(), param.getValue());
            query.setFirstResult(start);
            if (limit > 0)
                query.setMaxResults(limit);
            count = (long) query.getSingleResult();
            transaction.commit();
        } catch (PersistenceException e) {

        } finally {
            start = 0;
            limit = 0;
            params = new HashMap<>();
            if (manager.isOpen()) {
                manager.close();
            }
            // factory.close();
        }
        return count;
    }

    public Integer updateTyplesWithNamedQuery(final String namedQuery) {
        Integer affectedRows = 0;
        manager = factory.createEntityManager();
        transaction = manager.getTransaction();
        transaction.begin();
        try {
            query = manager.createNamedQuery(namedQuery);

            if (!params.isEmpty())
                for (Map.Entry<String, Object> param : params.entrySet())
                    query.setParameter(param.getKey(), param.getValue());

            affectedRows = query.executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {

        } finally {
            start = 0;
            limit = 0;
            params = new HashMap<>();
            if (manager.isOpen()) {
                manager.close();
            }
            // factory.close();
        }
        return affectedRows;
    }

    public <T> T insertTuple(final T tuple) {
        T t = null;
        manager = factory.createEntityManager();
        transaction = manager.getTransaction();
        transaction.begin();
        try {
            manager.persist(tuple);
            manager.flush();
            t = tuple;
            transaction.commit();
        } catch (EntityExistsException eee) {
            if (transaction.isActive())
                transaction.rollback();
        } catch (PersistenceException pe) {
            if (transaction.isActive())
                transaction.rollback();
        } finally {
            params = new HashMap<>();
            if (manager.isOpen()) {
                manager.close();
            }
        }
        return t;
    }

    public <T> T updateTuple(final T tuple) {
        T t = null;
        manager = factory.createEntityManager();
        transaction = manager.getTransaction();
        transaction.begin();
        try {
            t = manager.merge(tuple);
            manager.flush();
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive())
                transaction.rollback();
        } finally {
            params = new HashMap<>();
            if (manager.isOpen()) {
                manager.close();
            }
            // factory.close();
        }
        return t;
    }

    public Boolean removeTuple(String namedQuery) {
        Boolean success = true;
        manager = factory.createEntityManager();
        transaction = manager.getTransaction();
        transaction.begin();
        try {
            // manager.remove(t);
            query = manager.createNamedQuery(namedQuery);
            if (!params.isEmpty())
                for (Map.Entry<String, Object> param : params.entrySet())
                    query.setParameter(param.getKey(), param.getValue());
            query.executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive())
                transaction.rollback();
            success = false;
        } finally {
            params = new HashMap<>();
            if (manager.isOpen()) {
                manager.close();
            }
            // factory.close();
        }
        return success;
    }

    /*Help & Ideas from
    * https://stackoverflow.com/questions/5349264/total-row-count-for-pagination-using-jpa-criteria-api
    * https://stackoverflow.com/questions/12782023/how-to-combine-pagination-with-a-criteria-query-in-spring-data-jpa
    * TODO: ---> Make this function general to works with every entity and for each case. <---*/
    public FilteredTuples getFilteredTuples(final RoomEndpointFilterBean filter, final String sortField, final String order) {
        System.out.println("FILTER: " + filter.toString());

        /*Create Entity Manager*/
        manager = factory.createEntityManager();

        /*Create Criteria Builder*/
        final CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

        /*Predicates Array*/
        final List<Predicate> predicates = new ArrayList<Predicate>();

        /*Save filter values to local variables*/
        final String owner = filter.getOwner();
        final String country = filter.getCountry();
        final String locality = filter.getLocality();
        final Integer guests = filter.getGuests();
        final Integer minPrice = filter.getMin_price();
        final Integer maxPrice = filter.getMax_price();
        final Integer type = filter.getType();
        final Integer beds = filter.getBeds();
        final String checkInDate = filter.getCheckinDate();
        final String checkOutDate = filter.getCheckoutDate();

        /*Basic Query*/
        final CriteriaQuery<Object> Q = criteriaBuilder.createQuery(Object.class);
        final Root<RoomEntity> fromRooms = Q.from(RoomEntity.class);

        /*SQL: SELECT * FROM ROOMS*/
        Q.multiselect(fromRooms);

        /*To Find Min/Max overnight price*/
        final CriteriaQuery<Double[]> Q_PRICE = criteriaBuilder.createQuery(Double[].class);

        /*SQL: SELECT * FROM ROOMS*/
        Q_PRICE.multiselect(
                criteriaBuilder.min(fromRooms.<Integer>get("overnight_price")),
                criteriaBuilder.max(fromRooms.<Integer>get("overnight_price"))
        );

        /*To Find Total Records Count*/
        final CriteriaQuery<Long> cQuery = criteriaBuilder.createQuery(Long.class);
        final CriteriaQuery<Long> CQ = cQuery.select(criteriaBuilder.count(fromRooms));

        boolean hasPredicates = false;

        /*Predicate For Owner*/
        if (!StringUtils.isEmpty(owner)) {
            hasPredicates = true;

            /*SQL: JOIN HOSTENTITY*/
            Join<RoomEntity, HostEntity> associateJoin = fromRooms.join("hostEntity");
            predicates.add(criteriaBuilder.equal(associateJoin.<String>get("username"), filter.getOwner()));
        }

        if (!StringUtils.isEmpty(country) || !StringUtils.isEmpty(locality)) {
            hasPredicates = true;
            /*SQL: JOIN LocationEntity*/
            Join<RoomEntity, LocationEntity> associateJoin = fromRooms.join("locationEntity");
            /*Predicate For Country*/
            if (StringUtils.isEmpty(locality)) {
                predicates.add(criteriaBuilder.equal(associateJoin.<String>get("country"), filter.getCountry()));
            }
            /*Predicate For Locality*/
            else if (!StringUtils.isEmpty(locality)) {
                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.and(
                                        criteriaBuilder.equal(associateJoin.<String>get("locality"), filter.getLocality()),
                                        criteriaBuilder.equal(associateJoin.<String>get("country"), filter.getCountry())
                                ),
                                criteriaBuilder.equal(associateJoin.<String>get("administrative_area_level_5"), filter.getLocality()),
                                criteriaBuilder.equal(associateJoin.<String>get("administrative_area_level_4"), filter.getLocality()),
                                criteriaBuilder.equal(associateJoin.<String>get("administrative_area_level_3"), filter.getLocality()),
                                criteriaBuilder.equal(associateJoin.<String>get("administrative_area_level_2"), filter.getLocality()),
                                criteriaBuilder.equal(associateJoin.<String>get("administrative_area_level_1"), filter.getLocality())
                        ));
            }
        }

        /*Predicate For beds*/
        if (beds != null) {
            hasPredicates = true;
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            fromRooms.get("beds").as(Integer.class),
                            beds
                    )
            );
        }

        /*Predicate For Type*/
        if (type != null && type != 0) {
            hasPredicates = true;
            Join<RoomEntity, TypeEntity> roomEntityTypeEntityJoin = fromRooms.join("typeEntity", JoinType.INNER);
            predicates.add(
                    criteriaBuilder.equal(
                            fromRooms.get("typeEntity").get("id").as(Integer.class),
                            type
                    )
            );
        }


        /*Predicate For Guests*/
        if (guests != null) {
            hasPredicates = true;
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            fromRooms.get("max_people").as(Integer.class),
                            guests
                    )
            );
        }

        /*Predicate For Checkin And Checkout Dates*/
        java.sql.Date checkin = checkInDate != null ? java.sql.Date.valueOf(checkInDate) : null;
        java.sql.Date checkout = checkOutDate != null ? java.sql.Date.valueOf(checkOutDate) : null;

        if (checkin != null || checkout != null) {
            hasPredicates = true;
            /*SQL: JOIN CALENDARENTITY*/
            Join<RoomEntity, CalendarEntity> calendarEntityJoin = fromRooms.join("calendarEntities", JoinType.INNER);
            predicates.add(criteriaBuilder.and(
                    criteriaBuilder.equal(calendarEntityJoin.<String>get("available"), true),
                    criteriaBuilder.between(calendarEntityJoin.join("calendarId").<Date>get("date"), checkin, checkout)
            ));
        }

        /*SQL: WHERE */
        if (hasPredicates) {
            Q_PRICE.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        /*Prepare Query*/
        final TypedQuery<Double[]> TQ_PRICE = manager.createQuery(Q_PRICE);

        /*Execute The Dynamic Query.*/
        List<Double[]> resultList = TQ_PRICE.getResultList();

       /*Print The Dynamic Query*/
        System.out.println("\nTQ_PRICE:\n" + TQ_PRICE.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString() + "\n\n");

        Object[] prices = resultList.get(0);

        /*Predicate For minPrice, maxPrice*/
        if (minPrice != null && maxPrice != null) {
            hasPredicates = true;
            predicates.add(
                    criteriaBuilder.and(
                            criteriaBuilder.greaterThanOrEqualTo(
                                    fromRooms.get("overnight_price").as(Integer.class),
                                    minPrice
                            ),
                            criteriaBuilder.lessThanOrEqualTo(
                                    fromRooms.get("overnight_price").as(Integer.class),
                                    maxPrice
                            )
                    )
            );
        } else if (minPrice != null) {
            hasPredicates = true;
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            fromRooms.get("overnight_price").as(Integer.class),
                            minPrice
                    )
            );
        } else if (maxPrice != null) {
            hasPredicates = true;
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(
                            fromRooms.get("overnight_price").as(Integer.class),
                            maxPrice
                    )
            );
        }

        /*SQL: WHERE */
        if (hasPredicates) {
            Q.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
            CQ.where(predicates.toArray(new Predicate[0]));
        }

        if (checkin != null || checkout != null) {
            /*SQL: GROUP BY ROOMENTITY.id*/
            Q.groupBy(fromRooms.get("id"));
            int diffInDays = (int) ((checkout.getTime() - checkin.getTime()) / (1000 * 60 * 60 * 24)) + 1;
            Q.having(criteriaBuilder.ge(criteriaBuilder.count(fromRooms), diffInDays));
        }

        /*Sorting by sorting field*/
        if (!StringUtils.isEmpty(sortField)) {
            if (!StringUtils.isEmpty(order) && order.equalsIgnoreCase("DESC"))
                /*SQL: ORDER BY 'sortField' DESC*/
                Q.orderBy(criteriaBuilder.desc(fromRooms.get(sortField)));
            else
                /*SQL: ORDER BY 'sortField' ASC*/
                Q.orderBy(criteriaBuilder.asc(fromRooms.get(sortField)));
        }

        /*Prepare Count Query*/
        final TypedQuery<Long> C_TQ = manager.createQuery(CQ);
        C_TQ.setFirstResult(0);

        try {
            final long count = C_TQ.getSingleResult();

            /*Prepare Query*/
            final TypedQuery<Object> TQ = manager.createQuery(Q);

            /*SQL: LIMIT ?, ?*/
            TQ.setFirstResult(filter.getStart());
            TQ.setMaxResults(filter.getSize());

            List<Object> records;

            /*Execute The Dynamic Query.*/
            records = TQ.getResultList();

            /*Print The Dynamic Query*/
            System.out.println("\nDynamic Filtered Tuples Query: {count: " + count + "}\n" + TQ.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString() + "\n\n");

            if (manager.isOpen()) {
                manager.close();
            }
            // factory.close();
            return new FilteredTuples(records, count, prices);
        } catch (Exception e) {
            throw new ErrorException(Response.Status.INTERNAL_SERVER_ERROR, "Could not connect to database!");
        }
    }
}
