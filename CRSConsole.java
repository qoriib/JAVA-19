
/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Console driven UI for the Crisis Relief Services application.
 */
public class CRSConsole {
    /** Controller that manages domain logic. */
    private final CRS crs = new CRS();

    /** Shared scanner for console input. */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Application entry point.
     *
     * @param args CLI arguments (unused)
     */
    public static void main(String[] args) {
        new CRSConsole().run();
    }

    /**
     * Runs the main loop for the console UI.
     */
    private void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n==== CRS Console ====");
            System.out.println("Om Swastyastu!");
            System.out.println("1. Register Staff");
            System.out.println("2. Register Volunteer");
            System.out.println("3. Login as Staff");
            System.out.println("4. Login as Volunteer");
            System.out.println("5. List Trips");
            System.out.println("6. List All Users");
            System.out.println("0. Exit");
            int choice = readInt("Select option");
            try {
                switch (choice) {
                    case 1 -> handleRegisterStaff();
                    case 2 -> handleRegisterVolunteer();
                    case 3 -> handleStaffLogin();
                    case 4 -> handleVolunteerLogin();
                    case 5 -> listTrips();
                    case 6 -> listUsers();
                    case 0 -> exit = true;
                    default -> System.out.println("Unknown option");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        System.out.println("Goodbye!");
    }

    /**
     * Handles staff registration workflow.
     */
    private void handleRegisterStaff() {
        System.out.println("-- Register Staff --");
        String username = prompt("Username");
        String password = prompt("Password");
        String name = prompt("Name");
        String phone = prompt("Phone");
        String position = prompt("Position");
        LocalDate joined = readDate("Date joined (yyyy-MM-dd)");
        crs.registerStaff(username, password, name, phone, position, joined);
        System.out.println("Staff registered successfully.");
    }

    /**
     * Handles volunteer registration workflow.
     */
    private void handleRegisterVolunteer() {
        System.out.println("-- Register Volunteer --");
        String username = prompt("Username");
        String password = prompt("Password");
        String name = prompt("Name");
        String phone = prompt("Phone");
        crs.registerVolunteer(username, password, name, phone);
        System.out.println("Volunteer registered. You may now log in.");
    }

    /**
     * Performs staff login and opens session menu on success.
     */
    private void handleStaffLogin() {
        System.out.println("-- Staff Login --");
        Staff staff = (Staff) requireUser(prompt("Username"), prompt("Password"), Staff.class);
        staffSession(staff);
    }

    /**
     * Performs volunteer login and opens session menu on success.
     */
    private void handleVolunteerLogin() {
        System.out.println("-- Volunteer Login --");
        Volunteer volunteer = (Volunteer) requireUser(prompt("Username"), prompt("Password"), Volunteer.class);
        volunteerSession(volunteer);
    }

    /**
     * Authenticates and ensures the user is of the expected type.
     *
     * @param username username
     * @param password password
     * @param type     expected class type
     * @return authenticated user
     */
    private User requireUser(String username, String password, Class<? extends User> type) {
        User user = crs.authenticate(username, password);
        if (user == null || !type.isInstance(user)) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return user;
    }

    /**
     * Staff session menu loop.
     *
     * @param staff logged-in staff
     */
    private void staffSession(Staff staff) {
        System.out.println("Welcome, " + staff.getName());
        boolean logout = false;
        while (!logout) {
            System.out.println("\n-- Staff Menu --");
            System.out.println("1. Organize Trip");
            System.out.println("2. View My Trips");
            System.out.println("3. Manage Applications");
            System.out.println("0. Logout");
            int choice = readInt("Select option");
            try {
                switch (choice) {
                    case 1 -> organizeTrip(staff);
                    case 2 -> showStaffTrips(staff);
                    case 3 -> manageApplications(staff);
                    case 0 -> logout = true;
                    default -> System.out.println("Unknown option");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    /**
     * Guides staff through trip creation.
     *
     * @param staff organizer
     */
    private void organizeTrip(Staff staff) {
        System.out.println("-- Organize Trip --");
        LocalDate date = readDate("Trip date (yyyy-MM-dd)");
        String destination = prompt("Destination");
        String description = prompt("Description");
        CrisisType crisisType = readCrisisType();
        int required = readInt("Volunteers needed");
        Trip trip = crs.createTrip(staff.getUsername(), date, destination, description, crisisType, required);
        System.out.println("Trip created with ID: " + trip.getTripId());
    }

    /**
     * Displays trips organized by the given staff.
     *
     * @param staff organizer
     */
    private void showStaffTrips(Staff staff) {
        List<Trip> trips = crs.getTripsForStaff(staff.getUsername());
        if (trips.isEmpty()) {
            System.out.println("No trips organized yet.");
            return;
        }
        System.out.println("-- Your Organized Trips --");
        for (Trip trip : trips) {
            System.out.println("* Trip ID: " + trip.getTripId());
            System.out.println("  Date       : " + trip.getTripDate());
            System.out.println("  Destination: " + trip.getDestination());
            System.out.println("  Crisis     : " + trip.getCrisisType());
            System.out.println("  Needed     : " + trip.getNumVolunteers() + " volunteers");
            System.out.println("  Remaining  : " + trip.getRemainingSlots() + " slots");
            System.out.println("  Description: " + trip.getDescription());
        }
    }

    /**
     * Allows staff to review and update applications for their trips.
     *
     * @param staff organizer
     */
    private void manageApplications(Staff staff) {
        List<Trip> trips = crs.getTripsForStaff(staff.getUsername());
        if (trips.isEmpty()) {
            System.out.println("You have no trips to manage.");
            return;
        }
        System.out.println("-- Manage Applications --");
        System.out.println("Select a trip:");
        for (Trip trip : trips) {
            System.out.println("* " + trip.getTripId() + " - " + trip.getDescription()
                    + " (Date: " + trip.getTripDate() + ", Remaining: " + trip.getRemainingSlots() + ")");
        }
        String tripId = prompt("Trip ID");
        List<Application> applications = crs.getApplicationsForTrip(tripId);
        if (applications.isEmpty()) {
            System.out.println("No applications for this trip.");
            return;
        }
        printApplications(applications);
        String applicationId = prompt("Enter application ID to update (or blank to cancel)");
        if (applicationId.isBlank()) {
            return;
        }
        ApplicationStatus status = readApplicationStatus();
        String remarks = prompt("Remarks (optional)");
        crs.updateApplicationStatus(staff.getUsername(), applicationId, status,
                remarks.isBlank() ? null : remarks);
        System.out.println("Application updated.");
    }

    /**
     * Prints application details along with volunteer documents.
     *
     * @param applications list of applications
     */
    private void printApplications(List<Application> applications) {
        for (Application application : applications) {
            Volunteer volunteer = application.getVolunteer();
            System.out.println("Application " + application.getApplicationId());
            System.out.println("  Volunteer : " + volunteer.getName() + " (" + volunteer.getPhone() + ")");
            System.out.println("  Status    : " + application.getStatus());
            if (application.getRemarks() != null) {
                System.out.println("  Remarks   : " + application.getRemarks());
            }
            if (volunteer.getDocuments().isEmpty()) {
                System.out.println("  Documents: none");
            } else {
                System.out.println("  Documents:");
                for (Document document : volunteer.getDocuments()) {
                    System.out.println("    - Type  : " + document.getDocumentType());
                    if (document.getExpiryDate() != null) {
                        System.out.println("      Expiry: " + document.getExpiryDate());
                    }
                    if (document.getImagePath() != null) {
                        System.out.println("      Image : " + document.getImagePath());
                    }
                }
            }
        }
    }

    /**
     * Volunteer session menu loop.
     *
     * @param volunteer logged-in volunteer
     */
    private void volunteerSession(Volunteer volunteer) {
        System.out.println("Welcome, " + volunteer.getName());
        boolean logout = false;
        while (!logout) {
            System.out.println("\n-- Volunteer Menu --");
            System.out.println("1. Update Profile");
            System.out.println("2. Upload Document");
            System.out.println("3. View Trips and Apply");
            System.out.println("4. View My Applications");
            System.out.println("0. Logout");
            int choice = readInt("Select option");
            try {
                switch (choice) {
                    case 1 -> updateVolunteerProfile(volunteer);
                    case 2 -> uploadDocument(volunteer);
                    case 3 -> applyForTrip(volunteer);
                    case 4 -> showVolunteerApplications(volunteer);
                    case 0 -> logout = true;
                    default -> System.out.println("Unknown option");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    /**
     * Updates a volunteer profile.
     *
     * @param volunteer volunteer
     */
    private void updateVolunteerProfile(Volunteer volunteer) {
        System.out.println("-- Update Profile --");
        String name = prompt("New name (leave blank to keep)");
        String phone = prompt("New phone (leave blank to keep)");
        String password = prompt("New password (leave blank to keep)");
        crs.updateVolunteerProfile(volunteer.getUsername(),
                name.isBlank() ? null : name,
                phone.isBlank() ? null : phone,
                password.isBlank() ? null : password);
        System.out.println("Profile updated.");
    }

    /**
     * Uploads a document to the volunteer profile.
     *
     * @param volunteer volunteer
     */
    private void uploadDocument(Volunteer volunteer) {
        System.out.println("-- Upload Document --");
        DocumentType type = readDocumentType();
        LocalDate expiry = readOptionalDate("Expiry date (yyyy-MM-dd, blank if not applicable)");
        String image = prompt("Image/file reference");
        crs.addDocumentToVolunteer(volunteer.getUsername(), type, expiry,
                image.isBlank() ? null : image);
        System.out.println("Document added.");
    }

    /**
     * Allows volunteer to view trips and submit an application.
     *
     * @param volunteer volunteer
     */
    private void applyForTrip(Volunteer volunteer) {
        listTrips();
        String tripId = prompt("Enter trip ID to apply");
        Application application = crs.applyForTrip(volunteer.getUsername(), tripId);
        System.out.println("Application submitted with ID: " + application.getApplicationId());
    }

    /**
     * Displays applications belonging to the volunteer.
     *
     * @param volunteer volunteer
     */
    private void showVolunteerApplications(Volunteer volunteer) {
        List<Application> applications = crs.getApplicationsForVolunteer(volunteer.getUsername());
        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }
        for (Application application : applications) {
            Trip trip = application.getTrip();
            System.out.println("Trip " + trip.getTripId() + " on " + trip.getTripDate());
            System.out.println("  Description: " + trip.getDescription());
            System.out.println("  Status: " + application.getStatus());
            if (application.getRemarks() != null) {
                System.out.println("  Remarks: " + application.getRemarks());
            }
        }
    }

    /**
     * Lists all trips in the system.
     */
    private void listTrips() {
        List<Trip> trips = crs.getAllTrips();
        if (trips.isEmpty()) {
            System.out.println("No trips have been organized yet.");
            return;
        }
        System.out.println("-- Available Trips (Destinasi Bali sangat diutamakan) --");
        for (Trip trip : trips) {
            System.out.println("* Trip ID: " + trip.getTripId());
            System.out.println("  Date       : " + trip.getTripDate());
            System.out.println("  Destination: " + trip.getDestination());
            System.out.println("  Crisis     : " + trip.getCrisisType());
            System.out.println("  Needed     : " + trip.getNumVolunteers() + " volunteers");
            System.out.println("  Remaining  : " + trip.getRemainingSlots() + " slots");
            System.out.println("  Description: " + trip.getDescription());
        }
    }

    /**
     * Reads a crisis type selection from the user.
     *
     * @return selected crisis type
     */
    private CrisisType readCrisisType() {
        System.out.println("Select crisis type:");
        CrisisType[] values = CrisisType.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        while (true) {
            int idx = readInt("Choice") - 1;
            if (idx >= 0 && idx < values.length) {
                return values[idx];
            }
            System.out.println("Invalid selection.");
        }
    }

    /**
     * Reads an application status selection from the user.
     *
     * @return selected status
     */
    private ApplicationStatus readApplicationStatus() {
        System.out.println("Select status:");
        ApplicationStatus[] values = ApplicationStatus.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        while (true) {
            int idx = readInt("Choice") - 1;
            if (idx >= 0 && idx < values.length) {
                return values[idx];
            }
            System.out.println("Invalid selection.");
        }
    }

    /**
     * Reads a document type selection from the user.
     *
     * @return selected document type
     */
    private DocumentType readDocumentType() {
        System.out.println("Select document type:");
        DocumentType[] values = DocumentType.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        while (true) {
            int idx = readInt("Choice") - 1;
            if (idx >= 0 && idx < values.length) {
                return values[idx];
            }
            System.out.println("Invalid selection.");
        }
    }

    /**
     * Prompts for a line of text.
     *
     * @param label prompt label
     * @return input string trimmed
     */
    private String prompt(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }

    /**
     * Reads an integer with retry on invalid input.
     *
     * @param label prompt label
     * @return parsed integer
     */
    private int readInt(String label) {
        while (true) {
            try {
                System.out.print(label + ": ");
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a required date in ISO format.
     *
     * @param label prompt label
     * @return parsed date
     */
    private LocalDate readDate(String label) {
        while (true) {
            try {
                System.out.print(label + ": ");
                String line = scanner.nextLine().trim();
                return LocalDate.parse(line);
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }
    }

    /**
     * Reads an optional date in ISO format.
     *
     * @param label prompt label
     * @return parsed date or null if blank
     */
    private LocalDate readOptionalDate(String label) {
        while (true) {
            System.out.print(label + ": ");
            String line = scanner.nextLine().trim();
            if (line.isBlank()) {
                return null;
            }
            try {
                return LocalDate.parse(line);
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }
    }

    /**
     * Lists all users sorted by name.
     */
    private void listUsers() {
        List<User> users = crs.getAllUsersSortedByName();
        if (users.isEmpty()) {
            System.out.println("Belum ada pengguna terdaftar.");
            return;
        }
        System.out.println("-- Daftar Pengguna (urutan nama) --");
        for (User user : users) {
            String role = (user instanceof Staff) ? "Staff" : "Volunteer";
            System.out.println(role + ": " + user.getName() + " [" + user.getUsername() + "] " + user.getPhone());
        }
    }
}
