
/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Central controller coordinating CRS domain objects.
 */
public class CRS {
    /** All users keyed by username. */
    private final Map<String, User> users = new HashMap<>();

    /** Trips keyed by trip ID, preserving creation order. */
    private final Map<String, Trip> trips = new LinkedHashMap<>();

    /** Applications keyed by application ID, preserving creation order. */
    private final Map<String, Application> applications = new LinkedHashMap<>();

    /** Sequence for generating trip IDs. */
    private int tripSequence = 500;

    /** Sequence for generating application IDs. */
    private int applicationSequence = 1000;

    /**
     * Registers a new staff member.
     *
     * @param username   unique username
     * @param password   password credential
     * @param name       full name
     * @param phone      contact phone
     * @param position   job title
     * @param dateJoined date the staff joined
     * @return created staff object
     */
    public Staff registerStaff(
            String username,
            String password,
            String name,
            String phone,
            String position,
            LocalDate dateJoined) {
        ensureUniqueUsername(username);
        Staff staff = new Staff(username, password, name, phone, position, dateJoined);
        users.put(username, staff);
        return staff;
    }

    /**
     * Registers a new volunteer.
     *
     * @param username unique username
     * @param password password credential
     * @param name     full name
     * @param phone    contact phone
     * @return created volunteer object
     */
    public Volunteer registerVolunteer(String username, String password, String name, String phone) {
        ensureUniqueUsername(username);
        Volunteer volunteer = new Volunteer(username, password, name, phone);
        users.put(username, volunteer);
        return volunteer;
    }

    /**
     * Simple authentication helper.
     *
     * @param username username to verify
     * @param password password to verify
     * @return matching user or {@code null} if invalid
     */
    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Creates a new trip under the provided staff member.
     *
     * @param staffUsername organizer username
     * @param tripDate      date of trip
     * @param destination   destination
     * @param description   description
     * @param crisisType    crisis category
     * @param numVolunteers volunteers required
     * @return created trip
     */
    public Trip createTrip(String staffUsername,
            LocalDate tripDate,
            String destination,
            String description,
            CrisisType crisisType,
            int numVolunteers) {
        Staff staff = requireStaff(staffUsername);
        String tripId = String.format("T%04d", ++tripSequence);
        Trip trip = new Trip(tripId, tripDate, destination, description, crisisType, numVolunteers, staff);
        trips.put(tripId, trip);
        staff.addTrip(trip);
        return trip;
    }

    /**
     * @return immutable list of all trips in creation order
     */
    public List<Trip> getAllTrips() {
        return Collections.unmodifiableList(new ArrayList<>(trips.values()));
    }

    /**
     * Looks up a trip by ID.
     *
     * @param tripId id to find
     * @return found trip
     * @throws IllegalArgumentException if not found
     */
    public Trip findTrip(String tripId) {
        Trip trip = trips.get(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Trip not found: " + tripId);
        }
        return trip;
    }

    /**
     * Applies a volunteer to the specified trip if space is available.
     *
     * @param volunteerUsername applicant username
     * @param tripId            target trip ID
     * @return created application
     */
    public Application applyForTrip(String volunteerUsername, String tripId) {
        Volunteer volunteer = requireVolunteer(volunteerUsername);
        Trip trip = findTrip(tripId);
        ensureNotApplied(volunteer, trip);
        if (trip.getRemainingSlots() <= 0) {
            throw new IllegalStateException("Trip is already full");
        }
        String applicationId = String.format("A%05d", ++applicationSequence);
        Application application = new Application(applicationId, LocalDate.now(), ApplicationStatus.NEW,
                null, volunteer, trip);
        applications.put(applicationId, application);
        volunteer.addApplication(application);
        trip.addApplication(application);
        return application;
    }

    /**
     * Checks a volunteer has not already applied to the same trip.
     *
     * @param volunteer volunteer
     * @param trip      trip
     */
    private void ensureNotApplied(Volunteer volunteer, Trip trip) {
        for (Application application : volunteer.getApplications()) {
            if (application.getTrip() == trip) {
                throw new IllegalStateException("Volunteer already applied for this trip");
            }
        }
    }

    /**
     * Returns applications for a trip.
     *
     * @param tripId trip ID
     * @return immutable list of applications
     */
    public List<Application> getApplicationsForTrip(String tripId) {
        Trip trip = findTrip(tripId);
        return Collections.unmodifiableList(trip.getApplications());
    }

    /**
     * Returns applications for a volunteer.
     *
     * @param volunteerUsername volunteer username
     * @return immutable list of applications
     */
    public List<Application> getApplicationsForVolunteer(String volunteerUsername) {
        Volunteer volunteer = requireVolunteer(volunteerUsername);
        return Collections.unmodifiableList(volunteer.getApplications());
    }

    /**
     * Updates an application's status and remarks if the staff owns the trip.
     *
     * @param staffUsername staff username
     * @param applicationId application id
     * @param status        new status
     * @param remarks       optional remarks
     */
    public void updateApplicationStatus(String staffUsername, String applicationId,
            ApplicationStatus status, String remarks) {
        Staff staff = requireStaff(staffUsername);
        Application application = applications.get(applicationId);
        if (application == null) {
            throw new IllegalArgumentException("Application not found: " + applicationId);
        }
        if (application.getTrip().getOrganizer() != staff) {
            throw new IllegalStateException("Staff cannot modify this application");
        }
        if (status == ApplicationStatus.ACCEPTED && application.getTrip().getRemainingSlots() <= 0) {
            throw new IllegalStateException("Trip is already full");
        }
        application.setStatus(status);
        application.setRemarks(remarks);
    }

    /**
     * Adds a document to a volunteer profile.
     *
     * @param volunteerUsername volunteer username
     * @param type              document type
     * @param expiry            expiry or null
     * @param imagePath         image reference
     * @return created document
     */
    public Document addDocumentToVolunteer(String volunteerUsername, DocumentType type,
            LocalDate expiry, String imagePath) {
        Volunteer volunteer = requireVolunteer(volunteerUsername);
        Document document = new Document(type, expiry, imagePath);
        volunteer.addDocument(document);
        return document;
    }

    /**
     * Updates volunteer profile fields when provided.
     *
     * @param volunteerUsername volunteer username
     * @param newName           new name or null/blank to ignore
     * @param newPhone          new phone or null/blank to ignore
     * @param newPassword       new password or null/blank to ignore
     */
    public void updateVolunteerProfile(String volunteerUsername, String newName,
            String newPhone, String newPassword) {
        Volunteer volunteer = requireVolunteer(volunteerUsername);
        if (newName != null && !newName.isBlank()) {
            volunteer.setName(newName);
        }
        if (newPhone != null && !newPhone.isBlank()) {
            volunteer.setPhone(newPhone);
        }
        if (newPassword != null && !newPassword.isBlank()) {
            volunteer.setPassword(newPassword);
        }
    }

    /**
     * Returns trips organized by a staff member.
     *
     * @param staffUsername staff username
     * @return immutable trips list
     */
    public List<Trip> getTripsForStaff(String staffUsername) {
        Staff staff = requireStaff(staffUsername);
        return staff.viewTrips();
    }

    /**
     * @return unmodifiable collection of all users
     */
    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(users.values());
    }

    /**
     * Returns all users sorted by their full name.
     *
     * @return immutable list sorted by name ascending
     */
    public List<User> getAllUsersSortedByName() {
        List<User> sorted = new ArrayList<>(users.values());
        sorted.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return Collections.unmodifiableList(sorted);
    }

    /**
     * Ensures the requested username exists and is a staff.
     *
     * @param username username to check
     * @return staff instance
     */
    private Staff requireStaff(String username) {
        User user = users.get(username);
        if (user instanceof Staff staff) {
            return staff;
        }
        throw new IllegalArgumentException("Staff not found: " + username);
    }

    /**
     * Ensures the requested username exists and is a volunteer.
     *
     * @param username username to check
     * @return volunteer instance
     */
    private Volunteer requireVolunteer(String username) {
        User user = users.get(username);
        if (user instanceof Volunteer volunteer) {
            return volunteer;
        }
        throw new IllegalArgumentException("Volunteer not found: " + username);
    }

    /**
     * Checks for unique username.
     *
     * @param username username to assess
     */
    private void ensureUniqueUsername(String username) {
        Objects.requireNonNull(username, "username");
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
    }
}
