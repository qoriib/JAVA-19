
/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Staff members organize trips and process applications.
 */
public class Staff extends User {
    /** Role or job title of the staff member. */
    private String position;

    /** Date the staff member joined CRS. */
    private LocalDate dateJoined;

    /** Trips organized by this staff member. */
    private final List<Trip> organizedTrips = new ArrayList<>();

    /**
     * Creates a staff record.
     *
     * @param username   unique username
     * @param password   password credential
     * @param name       full name
     * @param phone      contact phone
     * @param position   job title
     * @param dateJoined joining date
     */
    public Staff(String username, String password, String name, String phone,
            String position, LocalDate dateJoined) {
        super(username, password, name, phone);
        this.position = Objects.requireNonNull(position, "position");
        this.dateJoined = Objects.requireNonNull(dateJoined, "dateJoined");
    }

    /**
     * @return job title
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets job title.
     *
     * @param position new title
     */
    public void setPosition(String position) {
        this.position = Objects.requireNonNull(position, "position");
    }

    /**
     * @return joining date
     */
    public LocalDate getDateJoined() {
        return dateJoined;
    }

    /**
     * Updates joining date.
     *
     * @param dateJoined new date
     */
    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = Objects.requireNonNull(dateJoined, "dateJoined");
    }

    /**
     * Registers a newly organized trip under this staff member.
     *
     * @param trip trip organized
     */
    void addTrip(Trip trip) {
        organizedTrips.add(trip);
    }

    /**
     * @return immutable view of trips organized by this staff
     */
    @Override
    public List<Trip> viewTrips() {
        return Collections.unmodifiableList(organizedTrips);
    }
}
