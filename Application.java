
/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a volunteer's request to participate in a trip.
 */
public class Application {
    /** Generated application identifier. */
    private final String applicationId;

    /** Date the application was created. */
    private final LocalDate applicationDate;

    /** Current status of the application. */
    private ApplicationStatus status;

    /** Remarks entered by staff upon review. */
    private String remarks;

    /** Applicant who submitted this application. */
    private final Volunteer volunteer;

    /** Trip the volunteer wishes to join. */
    private final Trip trip;

    /**
     * Creates a new application record.
     *
     * @param applicationId   unique identifier
     * @param applicationDate date of creation
     * @param status          initial status
     * @param remarks         optional remarks
     * @param volunteer       applicant
     * @param trip            trip applied to
     */
    public Application(
            String applicationId,
            LocalDate applicationDate,
            ApplicationStatus status,
            String remarks,
            Volunteer volunteer,
            Trip trip) {
        this.applicationId = Objects.requireNonNull(applicationId, "applicationId");
        this.applicationDate = Objects.requireNonNull(applicationDate, "applicationDate");
        this.status = Objects.requireNonNull(status, "status");
        this.remarks = remarks;
        this.volunteer = Objects.requireNonNull(volunteer, "volunteer");
        this.trip = Objects.requireNonNull(trip, "trip");
    }

    /**
     * @return unique application ID
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * @return creation date
     */
    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    /**
     * @return current status
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Updates the status.
     *
     * @param status new status
     */
    public void setStatus(ApplicationStatus status) {
        this.status = Objects.requireNonNull(status, "status");
    }

    /**
     * @return remarks or {@code null}
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Updates remarks.
     *
     * @param remarks new remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return applicant volunteer
     */
    public Volunteer getVolunteer() {
        return volunteer;
    }

    /**
     * @return trip being applied to
     */
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
