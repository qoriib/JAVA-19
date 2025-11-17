
/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Volunteer users who apply for CRS trips.
 */
public class Volunteer extends User {
    /** Documents submitted by the volunteer. */
    private final List<Document> documents = new ArrayList<>();

    /** Applications submitted by the volunteer. */
    private final List<Application> applications = new ArrayList<>();

    /**
     * Creates a volunteer record.
     *
     * @param username unique username
     * @param password password credential
     * @param name     full name
     * @param phone    contact phone
     */
    public Volunteer(String username, String password, String name, String phone) {
        super(username, password, name, phone);
    }

    /**
     * Adds a document to the volunteer profile.
     *
     * @param document document to add
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * @return immutable list of documents
     */
    public List<Document> getDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * @return immutable list of applications submitted
     */
    public List<Application> getApplications() {
        return Collections.unmodifiableList(applications);
    }

    /**
     * Tracks a new application made by this volunteer.
     *
     * @param application application added
     */
    void addApplication(Application application) {
        applications.add(application);
    }

    /**
     * @return immutable list of trips this volunteer has applied to
     */
    @Override
    public List<Trip> viewTrips() {
        List<Trip> trips = new ArrayList<>();
        for (Application application : applications) {
            trips.add(application.getTrip());
        }
        return Collections.unmodifiableList(trips);
    }
}
