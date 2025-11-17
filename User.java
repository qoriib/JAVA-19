
/**
 * Name: A. A Ngurah Aragon Udayana
 * Student ID: E2400070
 * BIT203 Advanced OO Programming
 * Assignment 1
 */
import java.util.List;
import java.util.Objects;

/**
 * Base abstraction for all CRS users.
 */
public abstract class User {
    /** Username used for authentication and identification. */
    private final String username;

    /** Password used for authentication. */
    private String password;

    /** Full name of the user. */
    private String name;

    /** Contact phone number for notifications. */
    private String phone;

    /**
     * Constructs a new user.
     *
     * @param username unique username
     * @param password password credential
     * @param name     full name
     * @param phone    contact phone
     */
    protected User(String username, String password, String name, String phone) {
        this.username = Objects.requireNonNull(username, "username");
        this.password = Objects.requireNonNull(password, "password");
        this.name = Objects.requireNonNull(name, "name");
        this.phone = Objects.requireNonNull(phone, "phone");
    }

    /**
     * @return the unique username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return current password value
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return full name
     */
    public String getName() {
        return name;
    }

    /**
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Updates the password.
     *
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = Objects.requireNonNull(password, "password");
    }

    /**
     * Updates the name.
     *
     * @param name new full name
     */
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    /**
     * Updates the phone number.
     *
     * @param phone new phone number
     */
    public void setPhone(String phone) {
        this.phone = Objects.requireNonNull(phone, "phone");
    }

    /**
     * Allows subclasses to expose the trips they are associated with.
     *
     * @return immutable list of trips
     */
    public abstract List<Trip> viewTrips();

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
