import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
/**
 * Volunteer users who apply for CRS trips.
 * Student: A. A Ngurah Aragon Udayana (E2400070), Date: 2024-11-14
 */
public class Volunteer extends User {
    private final List<Document> documents = new ArrayList<>();
    private final List<Application> applications = new ArrayList<>();

    public Volunteer(String username, String password, String name, String phone) {
        super(username, password, name, phone);
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public List<Document> getDocuments() {
        return Collections.unmodifiableList(documents);
    }

    public List<Application> getApplications() {
        return Collections.unmodifiableList(applications);
    }

    void addApplication(Application application) {
        applications.add(application);
    }

    @Override
    public List<Trip> viewTrips() {
        List<Trip> trips = new ArrayList<>();
        for (Application application : applications) {
            trips.add(application.getTrip());
        }
        return Collections.unmodifiableList(trips);
    }
}
