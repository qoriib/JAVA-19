import java.util.List;
import java.util.Objects;

/**
 * Base abstraction for all CRS users.
 * Student: A. A Ngurah Aragon Udayana (E2400070), Date: 2024-11-14
 */
public abstract class User {
    private final String username;
    private String password;
    private String name;
    private String phone;

    protected User(String username, String password, String name, String phone) {
        this.username = Objects.requireNonNull(username, "username");
        this.password = Objects.requireNonNull(password, "password");
        this.name = Objects.requireNonNull(name, "name");
        this.phone = Objects.requireNonNull(phone, "phone");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPassword(String password) {
        this.password = Objects.requireNonNull(password, "password");
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

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
