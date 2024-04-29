public class CourseFactory {
    public static Course createCourse(String courseName, String courseCode, String courseType, User user){
        String courseTypeLowerCase = courseType.toLowerCase();
        if(courseTypeLowerCase.equals("elective")) {
            return new ElectiveCourse(courseName, courseCode, user);
        }else if(courseTypeLowerCase.equals("compulsory")) {
            return new CompulsoryCourse(courseName, courseCode, user);
        }else if(courseTypeLowerCase.equals("seminar")) {
            return new SeminarCourse(courseName, courseCode, user);
        }else return null;
    }
}
