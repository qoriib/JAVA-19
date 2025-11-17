import java.time.LocalDate;
import java.util.Objects;

/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
/**
 * Represents a volunteer's request to participate in a trip.
 * Student: A. A Ngurah Aragon Udayana (E2400070), Date: 2024-11-14
 */
public class Application {
    private final String applicationId;
    private final LocalDate applicationDate;
    private ApplicationStatus status;
    private String remarks;
    private final Volunteer volunteer;
    private final Trip trip;

    public Application(String applicationId, LocalDate applicationDate, ApplicationStatus status,
                       String remarks, Volunteer volunteer, Trip trip) {
        this.applicationId = Objects.requireNonNull(applicationId, "applicationId");
        this.applicationDate = Objects.requireNonNull(applicationDate, "applicationDate");
        this.status = Objects.requireNonNull(status, "status");
        this.remarks = remarks;
        this.volunteer = Objects.requireNonNull(volunteer, "volunteer");
        this.trip = Objects.requireNonNull(trip, "trip");
    }

    public String getApplicationId() {
        return applicationId;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = Objects.requireNonNull(status, "status");
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public Trip getTrip() {
        return trip;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id='" + applicationId + '\'' +
                ", volunteer='" + volunteer.getUsername() + '\'' +
                ", trip='" + trip.getTripId() + '\'' +
                ", status=" + status +
                (remarks != null ? ", remarks='" + remarks + '\'' : "") +
                '}';
    }
}
