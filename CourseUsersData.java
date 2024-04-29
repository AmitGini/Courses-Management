import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CourseUsersData {
    private Set<User> users;

    public CourseUsersData(Set<User> users) {
        this.users = new HashSet<>(users); // Defensive copy to ensure encapsulation
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users); // Ensure read-only access
    }
}
