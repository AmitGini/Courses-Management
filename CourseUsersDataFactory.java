import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseUsersDataFactory {
    private static final Map<String, CourseUsersData> sharedUsersData = new HashMap<>();

    public static CourseUsersData getOrCreateCourseUsersData(Set<User> users) {
        String key = generateKey(users);
        return sharedUsersData.computeIfAbsent(key, k -> new CourseUsersData(users));
    }

    private static String generateKey(Set<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(User::getUsername))
                .map(User::getUsername)
                .collect(Collectors.joining(","));
    }
}