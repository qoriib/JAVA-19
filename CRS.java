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
 * Student: A. A Ngurah Aragon Udayana (E2400070), Date: 2024-11-14
 */
public class CRS {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Trip> trips = new LinkedHashMap<>();
    private final Map<String, Application> applications = new LinkedHashMap<>();
    private int tripSequence = 500;
    private int applicationSequence = 1000;

    /**
     * Registers a new staff member.
     */
    public Staff registerStaff(String username, String password, String name, String phone,
                               String position, LocalDate dateJoined) {
        ensureUniqueUsername(username);
        Staff staff = new Staff(username, password, name, phone, position, dateJoined);
        users.put(username, staff);
        return staff;
    }

    /**
     * Registers a new volunteer.
     */
    public Volunteer registerVolunteer(String username, String password, String name, String phone) {
        ensureUniqueUsername(username);
        Volunteer volunteer = new Volunteer(username, password, name, phone);
        users.put(username, volunteer);
        return volunteer;
    }

    /**
     * Simple authentication helper.
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
     */
    public Trip createTrip(String staffUsername, LocalDate tripDate, String destination,
                           String description, CrisisType crisisType, int numVolunteers) {
        Staff staff = requireStaff(staffUsername);
        String tripId = String.format("T%04d", ++tripSequence);
        Trip trip = new Trip(tripId, tripDate, destination, description, crisisType, numVolunteers, staff);
        trips.put(tripId, trip);
        staff.addTrip(trip);
        return trip;
    }

    public List<Trip> getAllTrips() {
        return Collections.unmodifiableList(new ArrayList<>(trips.values()));
    }

    public Trip findTrip(String tripId) {
        Trip trip = trips.get(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Trip not found: " + tripId);
        }
        return trip;
    }

    /**
     * Applies a volunteer to the specified trip if space is available.
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

    private void ensureNotApplied(Volunteer volunteer, Trip trip) {
        for (Application application : volunteer.getApplications()) {
            if (application.getTrip() == trip) {
                throw new IllegalStateException("Volunteer already applied for this trip");
            }
        }
    }

    public List<Application> getApplicationsForTrip(String tripId) {
        Trip trip = findTrip(tripId);
        return Collections.unmodifiableList(trip.getApplications());
    }

    public List<Application> getApplicationsForVolunteer(String volunteerUsername) {
        Volunteer volunteer = requireVolunteer(volunteerUsername);
        return Collections.unmodifiableList(volunteer.getApplications());
    }

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

    public Document addDocumentToVolunteer(String volunteerUsername, DocumentType type,
                                           LocalDate expiry, String imagePath) {
        Volunteer volunteer = requireVolunteer(volunteerUsername);
        Document document = new Document(type, expiry, imagePath);
        volunteer.addDocument(document);
        return document;
    }

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

    public List<Trip> getTripsForStaff(String staffUsername) {
        Staff staff = requireStaff(staffUsername);
        return staff.viewTrips();
    }

    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(users.values());
    }

    /**
     * Returns all users sorted by their full name; Bali nuance for display use.
     *
     * @return immutable list sorted by name ascending
     */
    public List<User> getAllUsersSortedByName() {
        List<User> sorted = new ArrayList<>(users.values());
        sorted.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return Collections.unmodifiableList(sorted);
    }

    private Staff requireStaff(String username) {
        User user = users.get(username);
        if (user instanceof Staff staff) {
            return staff;
        }
        throw new IllegalArgumentException("Staff not found: " + username);
    }

    private Volunteer requireVolunteer(String username) {
        User user = users.get(username);
        if (user instanceof Volunteer volunteer) {
            return volunteer;
        }
        throw new IllegalArgumentException("Volunteer not found: " + username);
    }

    private void ensureUniqueUsername(String username) {
        Objects.requireNonNull(username, "username");
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
    }
}
