import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
/**
 * Trip organized by CRS staff to respond to a crisis.
 * Student: A. A Ngurah Aragon Udayana (E2400070), Date: 2024-11-14
 */
public class Trip {
    private final String tripId;
    private final LocalDate tripDate;
    private final String destination;
    private final String description;
    private final CrisisType crisisType;
    private final int numVolunteers;
    private final Staff organizer;
    private final List<Application> applications = new ArrayList<>();

    public Trip(String tripId, LocalDate tripDate, String destination, String description,
                CrisisType crisisType, int numVolunteers, Staff organizer) {
        if (numVolunteers <= 0) {
            throw new IllegalArgumentException("Number of volunteers must be positive");
        }
        this.tripId = Objects.requireNonNull(tripId, "tripId");
        this.tripDate = Objects.requireNonNull(tripDate, "tripDate");
        this.destination = Objects.requireNonNull(destination, "destination");
        this.description = Objects.requireNonNull(description, "description");
        this.crisisType = Objects.requireNonNull(crisisType, "crisisType");
        this.numVolunteers = numVolunteers;
        this.organizer = Objects.requireNonNull(organizer, "organizer");
    }

    public String getTripId() {
        return tripId;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public String getDestination() {
        return destination;
    }

    public String getDescription() {
        return description;
    }

    public CrisisType getCrisisType() {
        return crisisType;
    }

    public int getNumVolunteers() {
        return numVolunteers;
    }

    public Staff getOrganizer() {
        return organizer;
    }

    public List<Application> getApplications() {
        return Collections.unmodifiableList(applications);
    }

    void addApplication(Application application) {
        applications.add(application);
    }

    public int getRemainingSlots() {
        int used = 0;
        for (Application application : applications) {
            if (application.getStatus() != ApplicationStatus.REJECTED) {
                used++;
            }
        }
        return numVolunteers - used;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id='" + tripId + '\'' +
                ", date=" + tripDate +
                ", destination='" + destination + '\'' +
                ", crisis=" + crisisType +
                ", required=" + numVolunteers +
                ", remaining=" + getRemainingSlots() +
                '}';
    }
}
