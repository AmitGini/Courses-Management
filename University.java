import java.util.ArrayList;
import java.util.List;

public class University {

    private static University instance = null;

    private List<Course> courses;
    private List<Student> students;
    private List<Practitioner> practitioners;
    private List<Lecturer> lecturers;

    private University(){
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
        this.practitioners = new ArrayList<>();
        this.lecturers = new ArrayList<>();}

    public static University getInstance(){
        if(instance == null){
            instance = new University();
        }
        return instance;
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public List<Practitioner> getPractitioners() {
        return this.practitioners;
    }

    public List<Lecturer> getLecturers() {
        return this.lecturers;
    }

    public boolean setStudent(Student student){
        if(student != null){
            this.students.add(student);
            return true;
        }
        return false;
    }

    public boolean setPractitioner(Practitioner practitioner){
        if(practitioner != null){
            this.practitioners.add(practitioner);
            return true;
        }
        return false;
    }

    public boolean setLecturer(Lecturer lecturer){
        if(lecturer != null){
            this.lecturers.add(lecturer);
            return true;
        }
        return false;
    }

    public boolean setCourse(Course course){
        if(course != null){
            this.courses.add(course);
            return true;
        }
        return false;
    }

    public boolean setRemoveCourse(Course course){
        if(course != null){
            this.courses.remove(course);
            return true;
        }
        return false;
    }

    public User getUserByName(String username) {
        for (Student student : this.students) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }
        for (Practitioner practitioner : this.practitioners) {
            if (practitioner.getUsername().equals(username)) {
                return practitioner;
            }
        }
        for (Lecturer lecturer : this.lecturers) {
            if (lecturer.getUsername().equals(username)) {
                return lecturer;
            }
        }
        return null;
    }
    
    public Course getCourseByID(String courseID) {
        for (Course course : this.courses) {
            if (course.getID().equals(courseID)) {
                return course;
            }
        }
        return null;
    }

    public void removeCourseFromEveryone(Course course) {
        for (Student student : this.students) {
            student.setRemoveCourse(course);
        }
        for (Practitioner practitioner : this.practitioners) {
            practitioner.setRemoveCourse(course);
        }
        for (Lecturer lecturer : this.lecturers) {
            lecturer.setRemoveCourse(course);
        }
    }
}
