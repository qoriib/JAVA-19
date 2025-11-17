import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Staff members organize trips and process applications.
 * Student: A. A Ngurah Aragon Udayana (E2400070), Date: 2024-11-14
 */
public class Staff extends User {
    private String position;
    private LocalDate dateJoined;
    private final List<Trip> organizedTrips = new ArrayList<>();

    public Staff(String username, String password, String name, String phone,
                 String position, LocalDate dateJoined) {
        super(username, password, name, phone);
        this.position = Objects.requireNonNull(position, "position");
        this.dateJoined = Objects.requireNonNull(dateJoined, "dateJoined");
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = Objects.requireNonNull(position, "position");
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = Objects.requireNonNull(dateJoined, "dateJoined");
    }

    void addTrip(Trip trip) {
        organizedTrips.add(trip);
    }

    @Override
    public List<Trip> viewTrips() {
        return Collections.unmodifiableList(organizedTrips);
    }
}
