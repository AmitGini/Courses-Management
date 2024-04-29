public class CompulsoryCourse extends Course{
    public CompulsoryCourse(String courseName, String courseID, User user) {
        super(courseName, courseID, user);
    }

    @Override
    public String getCourseType(){
        return "Compulsory Course";
    }
}
