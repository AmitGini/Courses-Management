public class SeminarCourse extends Course{
    public SeminarCourse(String courseName, String courseID, User user) {
        super(courseName, courseID, user);
    }

    @Override
    public String getCourseType() {
        return "Seminar Course";
    }
}
