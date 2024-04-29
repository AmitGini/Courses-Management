public class Student extends User implements CourseNotifier {

    private int minPassGrade;

    public Student(String username, String password) {
        super(username, password);

    }

    /*
     * Set the grade of a course
     */
    public void setGrade(Course course, int grade) {
        this.myCourses.put(course, grade);
    }

    /*
        * Get the grade of a course
     */
    public int getGrade(Course course){
        return this.myCourses.get(course);
    }

    /*
        * Printing the grades of the student
     */
    public void printAllCourseGrades(){
        this.myCourses.keySet().forEach(course -> {
            String isGradeSubmitted = (this.myCourses.get(course) == -1) ? "Grade not submitted" : "Grade: " + this.myCourses.get(course);
            System.out.println("Course Type:"+ course.getCourseType() + course.getName() + " - " + isGradeSubmitted);
        });
    }


    public void printCourseGrade(Course course) {
        if (this.myCourses.containsKey(course)) {
            String isGradeSubmitted = (this.myCourses.get(course) == -1) ? "Grade not submitted" : "Grade: " + this.myCourses.get(course);
            System.out.println("Course "+ course.getName() + " Type: "+ course.getCourseType() + " " + isGradeSubmitted);
        }
    }

    @Override
    public void update(String courseName) {
        String message = "Student notification: " + courseName + " is now available!";
        this.myNotifications.add(message);
    }

}
