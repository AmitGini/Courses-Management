import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class User {
    protected String myUsername;
    protected String myPassword;
    protected List<String> myNotifications;
    protected HashMap<Course, Integer> myCourses;


    public User(String username, String password){
        this.myUsername = username;
        this.myPassword = password;
        this.myCourses = new HashMap<>();
        this.myNotifications = new ArrayList<>();
    }

    public String getUsername(){
        return this.myUsername;
    }

    public String getPassword() {return this.myPassword; }

    public int getNumOfCourses() {
        return this.myCourses.size();
    }

    public void setCourse(Course course) {
        this.myCourses.put(course, -1);
    }

    public void setRemoveCourse(Course course) {
        this.myCourses.remove(course);
    }

    public boolean equals(User user) {
        if(this.myUsername.equals(user.getUsername()) && this.myPassword.equals(user.getPassword())) {
            return true;
        }else{
            return false;
        }
    }

    public void printMyCourses() {
        for (Course course : this.myCourses.keySet()) {
            System.out.println("Course Type: "+ course.getCourseType() + ", " + course.getName() + " - ID: " + course.getID() );
        }
    }

    public void printNotifications() {
        System.out.println("**** Notifications for " + this.myUsername + " ****");
        for (String notification : myNotifications) {
            System.out.println(notification);
        }
    }

    public boolean isAllowedToCreateCourse() {
        if(this instanceof Lecturer || this instanceof Practitioner) {
            return true;
        }else{
            return false;
        }
    }

}

