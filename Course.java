import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Set;

public abstract class Course {

    private CourseUsersData teachersData;
    private CourseNotificationService courseNotification;

    private List<Student> studentList;
    private String courseName;
    private int capacity;
    private String courseID;

    private Boolean statusAvailableSpots;

    public Course(String courseName, String courseID, User user) {
        if(user instanceof Student){
            System.out.println("**** User is a Student Therefore Cannot Create Course****");
        }else{
            Set<User> initialUsers = Collections.singleton(user);
            this.teachersData = CourseUsersDataFactory.getOrCreateCourseUsersData(initialUsers);
            this.studentList = new ArrayList<>();
            this.courseNotification = new CourseNotificationService();
            this.courseName = courseName;
            this.courseID = courseID;
            this.capacity = 30;
            this.statusAvailableSpots = true;
        }
    }

    public abstract String getCourseType();

    public String getName() {
        return this.courseName;
    }

    public String getID() {
        return this.courseID;
    }

    public boolean getIsCourseFull() {
        return !this.statusAvailableSpots;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        if(capacity < 1) {
            System.err.println("**** Course Capacity can be only a positive number ****");
            return;
        }
        this.capacity = capacity;
        updateAvailableSpots();
    }

    public void setRegisterStudent(Student student){
        if(student == null) {
            return;
        }
        else if(this.studentList.size() == this.capacity){
            return;
        }
        else if(this.studentList.contains(student)) {
            return;
        }
        else{
            this.studentList.add(student);
            if(this.courseNotification.isSubscriber(student)) {
                this.courseNotification.removeSubscriber(student);
                updateAvailableSpots();
            }
        }
    }

    public void setUnregisterStudent(Student student){
        if(student == null) {
            return;
        }
        else if(!this.studentList.contains(student)) {
            return;
        }
        else {
            this.studentList.remove(student);
            updateAvailableSpots();
        }
    }

    public void setPractitioner(Practitioner practitioner) {
        if(practitioner == null) {
            return;
        }
        else if(this.teachersData.getUsers().contains(practitioner)) {
            System.out.println("**** Practitioner is already added ****");
        }
        else {
            this.teachersData.addUser(practitioner);
            refreshUsersData();
            System.out.println("Successfully added Practitioner to the course");
        }
    }

    public void setLecturer(Lecturer lecturer) {
        if(lecturer == null) {
            return;
        }
        else if(this.teachersData.getUsers().contains(lecturer)) {
            System.out.println("**** Lecturer is already added ****");
        }
        else {
            this.teachersData.addUser(lecturer);
            refreshUsersData();
            System.out.println("Successfully added Lecturer to the course");
        }
    }

    public void setChangeSubscriptionCourse(User user){
        if(user instanceof Student){
            if(!this.courseNotification.isSubscriber(user)){
                this.courseNotification.addSubscriber(user);
            }else{
                this.courseNotification.removeSubscriber(user);
            }
        }
    }

    public boolean isACourseLecturer(User user) {
        if (user instanceof Lecturer) {
            for (User users : this.teachersData.getUsers()) {
                if (users.equals(user)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isACourseStudent(String studentUsername){
        for(Student student: this.studentList){
            if(student.getUsername().equals(studentUsername)){
                return true;
            }
        }
        return false;
    }

    public void showSubscribers(){
        this.courseNotification.showSubscribers();
    }

    public void showCourseStudents(){
        System.out.println("\nCourse Students: \n");
        for(Student student: this.studentList){
            System.out.println(student.getUsername());
        }
        System.out.println("\nIn total: " + this.studentList.size() +"/"+this.capacity);
    }

    private void updateAvailableSpots(){
        boolean statusBeforeUpdate = this.statusAvailableSpots;
        this.statusAvailableSpots = (this.capacity-this.studentList.size() > 0);
        if(statusBeforeUpdate != this.statusAvailableSpots){
            this.courseNotification.updateAll(this.courseName);
        }
    }

    private void refreshUsersData() {
        // Re-fetch the CourseUsersData to synchronize with any other courses that might share the same set of users
        this.teachersData = CourseUsersDataFactory.getOrCreateCourseUsersData(new HashSet<>(teachersData.getUsers()));
    }

}
