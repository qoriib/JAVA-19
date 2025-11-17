
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
 * Trip organized by CRS staff to respond to a crisis.
 */
public class Trip {
    /** Generated trip identifier. */
    private final String tripId;

    /** Scheduled date of the trip. */
    private final LocalDate tripDate;

    /** Destination or location. */
    private final String destination;

    /** Description of activities. */
    private final String description;

    /** Crisis category for this trip. */
    private final CrisisType crisisType;

    /** Number of volunteers needed. */
    private final int numVolunteers;

    /** Organizer staff member. */
    private final Staff organizer;

    /** Applications associated with this trip. */
    private final List<Application> applications = new ArrayList<>();

    /**
     * Creates a new trip.
     *
     * @param tripId        identifier
     * @param tripDate      date of trip
     * @param destination   destination name
     * @param description   description text
     * @param crisisType    crisis category
     * @param numVolunteers required volunteer count (>0)
     * @param organizer     organizing staff
     */
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

    /**
     * @return trip ID
     */
    public String getTripId() {
        return tripId;
    }

    /**
     * @return date of trip
     */
    public LocalDate getTripDate() {
        return tripDate;
    }

    /**
     * @return destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return crisis type
     */
    public CrisisType getCrisisType() {
        return crisisType;
    }

    /**
     * @return required volunteer count
     */
    public int getNumVolunteers() {
        return numVolunteers;
    }

    /**
     * @return organizer staff
     */
    public Staff getOrganizer() {
        return organizer;
    }

    /**
     * @return immutable list of applications
     */
    public List<Application> getApplications() {
        return Collections.unmodifiableList(applications);
    }

    /**
     * Adds an application to this trip.
     *
     * @param application application to add
     */
    void addApplication(Application application) {
        applications.add(application);
    }

    /**
     * Calculates remaining volunteer slots (excluding rejected applications).
     *
     * @return number of open slots
     */
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
