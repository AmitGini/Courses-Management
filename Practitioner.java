import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Practitioner extends User{

    private List<Course> myCourses;

    public Practitioner(String username, String password) {
        super(username, password);
        this.myCourses = new ArrayList<>();
    }

    @Override
    public void printMyCourses() {
        if(this.myCourses.size() == 0) {
            System.out.println("No courses available");
        }else{
            for (Course course : this.myCourses) {
                System.out.println(course.getName() + " - ID: " + course.getID());
            }
        }
    }

    @Override
    public void setCourse(Course course) {
        this.myCourses.add(course);
    }

    @Override
    public void setRemoveCourse(Course course) {
        this.myCourses.remove(course);
    }
    @Override
    public int getNumOfCourses() {
        return this.myCourses.size();
    }

}
