import java.util.ArrayList;
import java.util.List;

public class ElectiveCourse extends Course{

    public ElectiveCourse(String courseName, String courseID, User user) {
        super(courseName, courseID, user);
    }

    @Override
    public String getCourseType() {
        return "Elective Course";
    }

}
