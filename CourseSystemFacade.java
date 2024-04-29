import java.util.ArrayList;
import java.util.List;

/*
    * CourseSystemFacade class is a class that management courses in a university hierarchy model.
    * It has the ability to login, sign up, logout, set new course, remove course, set lecturer/practitioner to course.
    * It also has the ability to register/unregister student to course, set course grade to a student and get course.
    * It has the ability to get user and show all university courses, and more...
 */
public class CourseSystemFacade {

    private boolean printingMessageFlag = false;
    private boolean printingErrorMessageFlag = false;
    private int capacity; // the default capacity is 100 of connected users at the same time.
    private List<User> connectedUsers;  // list of connected students.
    private University universityInstance;  // the university instance - a class that holds all the university data, using singleton.


    /*
        * Constructor for CourseSystemFacade class.
        * It initializes the capacity to 100 and creates a new list of connected students.
        * It also initializes the university instance.
     */
    public CourseSystemFacade(){
        this.capacity = 100;
        this.connectedUsers = new ArrayList<>();
        this.universityInstance = University.getInstance();
    }

    /*
        * Method setStatusPrintingMessageFlag is a method that sets the printing message flag.
        * It gets a boolean as a parameter and sets the printing message flag to this boolean.
     */
    public void setStatusPrintingMessageFlag(boolean status){
        this.printingMessageFlag = status;
     }

     /*
        * Method setStatusPrintingErrorMessageFlag is a method that sets the printing error message flag.
        * It gets a boolean as a parameter and sets the printing error message flag to this boolean.
     */
     public void setStatusPrintingErrorMessageFlag(boolean status){
        this.printingErrorMessageFlag = status;
     }

    /*
        * Method setCapacity is a method that sets the capacity of the connected students.
        * It gets an integer as a parameter and sets the capacity to this integer.
     */
    public void setSystemCapacity(int capacity) {
        if(capacity < 1) {
            System.err.println("Capacity must be greater than 0");
        }
        else if(connectedUsers.size() > capacity) {
            System.err.println("The capacity is smaller than the number of connected students at this moment. Enter larger capacity, Or disconnect some users.");
        }
        else{
            this.capacity = capacity;
        }
    }

    /*
        * Method setNewCourse is a method that creates a new course.
        * It gets the course name, course ID, course type and the user that wants to create the course.
        * It checks if the course already exists, if it does, it returns the course.
        * If the course does not exist, it checks if the user is allowed to create a course(User is Practitioner or Lecturer).
        * If the user is allowed to create a course, it creates a new course and sets it to the university instance.
        * It also sets the course to the user.
        * It returns the course.
     */
    public Course setNewCourse(String courseName, String courseID, String courseType, String username) {

        User user = getUser(username);
        if(user == null){
            if(printingErrorMessageFlag) System.err.println("User does not exist");
            return null;

        }else if(!isConnected(username)){
            if(printingErrorMessageFlag) System.err.println("User is not connected");
            return null;

        }else{
            Course tempCourse = universityInstance.getCourseByID(courseID);

            if(tempCourse != null){
                if(printingErrorMessageFlag) System.err.println("Course with that ID already exists");
                return tempCourse;
            }
            else{
                if(user.isAllowedToCreateCourse()){
                    tempCourse = CourseFactory.createCourse(courseName, courseID, courseType , user);
                    if(tempCourse != null){
                        if(this.universityInstance.setCourse(tempCourse)) {
                            user.setCourse(tempCourse);
                        }else{
                            if(printingErrorMessageFlag) System.err.println("Course could not be added to the university instance");

                        }
                    }
                    else{
                        if(printingErrorMessageFlag) System.err.println("String courseType might entered wrongly, try compulsory/elective/seminar");}
                }
                else{
                    if(printingErrorMessageFlag) System.err.println("User is not allowed to create a course");
                }
            }
            return tempCourse;
        }
    }

    /*
        * Method setRemoveCourse is a method that removes a course.
        * It gets the course ID and the user that wants to remove the course.
        * It checks if the course exists, if it does, it removes the course from the university instance.
        * It also removes the course from everyone.
     */
    public boolean setRemoveCourse(String courseID, String username) {
        Course course = this.universityInstance.getCourseByID(courseID);
        User user = getUser(username);
        if(course != null){
            if(user != null) {
                if (course.isACourseLecturer(user)) {
                    if(this.universityInstance.setRemoveCourse(course)){
                    this.universityInstance.removeCourseFromEveryone(course);
                    return true;
                    }
                    else{
                        if(printingErrorMessageFlag) System.err.println("Course could not be removed from the university instance");
                    }
                }
                else {
                    if(printingErrorMessageFlag) System.err.println("is not a lecturer of the course");
                }
            }
            else {
                if(printingErrorMessageFlag) System.err.println("User does not exist");
            }
        }
        else {
            if(printingErrorMessageFlag) System.err.println("Course does not exist");
        }
        return false;
    }

    /*
        * Method setCourseCapacity is a method that sets the capacity of a course.
        * It gets the course ID, the username and the capacity.
        * It checks if the user exists and if the user is allowed to change the course capacity.
        * If the user is allowed to create a course, it gets the course and sets the capacity.
     */
    public void setCourseCapacity(String courseID, String username, int capacity){
        User user = getUser(username);
        if(user == null || !user.isAllowedToCreateCourse()){
            if(printingErrorMessageFlag)System.err.println("User does not exist or is not allowed to create a course");
        }else{
            Course course = getCourseByID(courseID);
            if(course != null){
                course.setCapacity(capacity);
            }else if(printingErrorMessageFlag) System.err.println("Course does not exist");
        }
    }

    /*
        * Method setLecturerPractitionerToCourse is a method that sets a lecturer/practitioner to a course.
        * It gets the course, the username and the user.
        * It checks if the course and the user exist.
        * If the user is a lecturer, it sets the lecturer to the course.
        * If the user is a practitioner, it sets the practitioner to the course.
     */
    public boolean setLecturerPractitionerToCourse(String courseID, String username) {
        Course course = getCourseByID(courseID);
        User teacherUser = getUser(username);

        if (course != null && teacherUser != null) {
            if(!(teacherUser instanceof Student)){

                if(teacherUser instanceof Lecturer){
                    course.setLecturer((Lecturer) teacherUser);
                    teacherUser.setCourse(course);
                    return true;
                }

                else if(teacherUser instanceof Practitioner){
                    course.setPractitioner((Practitioner) teacherUser);
                    teacherUser.setCourse(course);
                    return true;
                }
            } else {
                if(printingErrorMessageFlag) System.err.println("User does not exist or is a student");
            }
        }
        else{
            if(printingErrorMessageFlag) System.err.println("Course does not exist");
        }
        return false;
    }

    /*
        * Method setStudentRegistrationToCourse is a method that sets a student to a course.
        * It gets the user and the course ID.
        * It checks if the course exists.
        * If the course does not exist, it returns -1.
        * If the course does exist and there are no available spots, it returns 0.
        * If the course does exist and there are available spots, it sets the student to the course and returns 1.
     */
    public boolean setStudentRegistrationToCourse(String courseID, String username) {
        User user = getUser(username);
        Course course = getCourseByID(courseID);
        if(course == null){
            if(printingErrorMessageFlag) System.err.println("Course does not exist");
        }
        else if(course.getIsCourseFull()){
            if(printingMessageFlag) System.out.println("No available spots");
        }
        else {
            course.setRegisterStudent((Student) user);
            user.setCourse(course);
            if(printingMessageFlag) System.out.println("Student registered successfully");
            return true;
        }
        return false;
    }

    /*
        * Method setStudentUnRegistrationToCourse is a method that sets a student to a course.
        * It gets the user and the course.
        * It checks if the course exists.
        * If the course does not exist, it returns false.
        * If the course does exist, it sets the student to the course and returns true.
     */
    public boolean setStudentUnRegistrationToCourse(String courseID, String username) {
        Course course = getCourseByID(courseID);
        User user = getUser(username);

        if(course == null){
            if(printingErrorMessageFlag) System.err.println("Course does not exist");
        }
        else if(user == null){
            if(printingErrorMessageFlag) System.err.println("User does not exist");
        }
        else {
            if(getGrade(courseID,username) == -1){
                course.setUnregisterStudent((Student) user);
                user.setRemoveCourse(course);
                if(printingMessageFlag) System.out.println("Student unregistered successfully");
                return true;
            }
            else{
                if(printingErrorMessageFlag) System.err.println("Student has a grade in the course, cannot unregister");
            }
        }
        return false;
    }

    /*
        * Method setCourseGradeToStudent is a method that sets a grade to a student in a course.
        * It gets the course, the student username, the grade and the user.
        * It checks if the course exists.
        * If the course does not exist, it returns false.
        * If the course does exist, it checks if the user is a lecturer of the course and if the student is in the course.
        * If the user is a lecturer of the course and the student is in the course, it sets the grade to the student.
     */
    public boolean setCourseGradeToStudent(String lecturerUsername, String studentUsername, String courseID, int grade) {
        User lecturer = getUser(lecturerUsername);
        User student = getUser(studentUsername);
        Course course = getCourseByID(courseID);
        if(course == null) {
            if (printingErrorMessageFlag) System.err.println("Course does not exist");
        }
        else if(lecturer == null || student == null) {
            if (printingErrorMessageFlag) System.err.println("Lecturer Or Student does not exist");
        }
        else if(!this.connectedUsers.contains(lecturer)){
            if(printingErrorMessageFlag) System.err.println("Lecturer is not connected");
        }
        else if (course.isACourseLecturer(lecturer)) {
            if (course.isACourseStudent(studentUsername)) {
                ((Student) student).setGrade(course,grade);
                return true;
            }
            else {
                if(printingMessageFlag) System.out.println("Student is not in the course");
            }
        }
        else {
            if(printingErrorMessageFlag) System.err.println("Lecturer is not a lecturer of the course");
        }
        return false;
    }

    /*
        * Method setChangeSubscriptionCourse is a method that changes the subscription of a course.
        * It gets the user and the course.
        * It checks if the user is a student.
        * If the user is a student, it adds the student to the course.
        * If the user is a student and is already in the course, it removes the student from the course.
     */
    public boolean setChangeSubscriptionCourse(String courseID, String username){
        User user = getUser(username);
        Course course = getCourseByID(courseID);
        if(user != null && course != null){
            course.setChangeSubscriptionCourse(user);
            if(printingMessageFlag) System.out.println("Subscription changed successfully");
            return true;
        }else if(printingErrorMessageFlag) System.err.println("User or Course does not exist");
        return false;
    }

    /*
        * Method getCourse is a method that gets a course by its ID.
        * It gets the course ID.
        * It returns the course.
     */
    public Course getCourseByID(String courseID) {
        return this.universityInstance.getCourseByID(courseID);
    }

    /*
        * Method getCourseNameByID is a method that gets a course name by its ID.
        * It gets the course ID.
        * It returns the course name.
     */
    public String getCourseNameByID(String courseID){
        Course course = getCourseByID(courseID);
        if(course != null){
            return course.getName();
        }
        else {
            if(printingErrorMessageFlag) System.err.println("Course does not exist");
            return null;
        }
    }

    /*
        * Method getUser is a method that gets a user by its username.
        * It gets the username.
        * It returns the user.
     */
    public User getUser(String username) {
        return this.universityInstance.getUserByName(username);
    }

    /*
        * Method getCapacity is a method that gets the capacity of the connected students.
        * It returns the capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /*
        * Method getGrade is a method that gets the grade of a student in a course.
        * It gets the course ID and the username.
        * It checks if the course exists.
        * If the course does not exist, it returns -1.
        * If the course does exist, it checks if the student is in the course.
        * If the student is in the course, it returns the grade of the student.
     */
    public int getGrade(String courseID, String username){
        Course course = getCourseByID(courseID);
        if(course == null){
            if(printingErrorMessageFlag) System.err.println("Course does not exist");
            return -1;
        }
        User user = getUser(username);
        if(user == null){
            if(printingErrorMessageFlag) System.err.println("User does not exist");
            return -1;
        }
        if(course.isACourseStudent(username)){
            return ((Student) user).getGrade(course);
        }else{
            if(printingErrorMessageFlag) System.err.println("Student is not in the course");
            return -1;
        }

    }

    /*
     * Method login is a method that checks if the username and password are correct.
     * If they are correct, it returns the user that logged in.
     * If they are not correct, it returns null.
     * It also adds the user to the connected students list.
     */
    public boolean login(String username, String password) {
        if(this.connectedUsers.size() == this.capacity){
            if(printingErrorMessageFlag) System.err.println("The capacity of connected students is full. Please try again later.");
            return false;
        }
        List<Lecturer> lecturersInstance = this.universityInstance.getLecturers();
        List<Practitioner> practitionersInstance = this.universityInstance.getPractitioners();
        List<Student> studentInstance = this.universityInstance.getStudents();
        for(Student student: studentInstance){
            if(student.getUsername().equals(username) && student.getPassword().equals(password)){
                if(printingMessageFlag) System.out.println("Welcome " + username + "!");
                this.connectedUsers.add(student);
                return true;
            }
        }
        for(Practitioner practitioner: practitionersInstance){
            if(practitioner.getUsername().equals(username) && practitioner.getPassword().equals(password)){
                if(printingMessageFlag) System.out.println("Welcome " + username + "!");
                this.connectedUsers.add(practitioner);
                return true;
            }
        }
        for(Lecturer lecturer: lecturersInstance){
            if(lecturer.getUsername().equals(username) && lecturer.getPassword().equals(password)){
                if(printingMessageFlag) System.out.println("Welcome " + username + "!");
                this.connectedUsers.add(lecturer);
                return true;
            }
        }
        if(printingErrorMessageFlag) System.err.println("Username or password is incorrect. Please try again.");
        return false;
    }

    /*
     * Method sign_up is a method that checks if the username is already in use.
     * If the username is already in use, it returns null.
     * If the username is not in use, it creates a new user and returns it.
     * It also adds the user to the connected students list.
     */
    public boolean sign_up(String username, String password, String userType){
        boolean signupStatus = false;
        if(isUsernameInUse(username)){
            if(printingErrorMessageFlag) System.err.println("Username already in use. Please try again.");
        }else{
            User newUser = UserFactory.createUser(username, password, userType);
            if(newUser != null){
                switch(userType) {
                    case "student":
                        this.universityInstance.setStudent((Student) newUser);
                        signupStatus = true;
                        break;
                    case "practitioner":
                        this.universityInstance.setPractitioner((Practitioner) newUser);
                        signupStatus = true;
                        break;
                    case "lecturer":
                        this.universityInstance.setLecturer((Lecturer) newUser);
                        signupStatus = true;
                        break;
                    default:
                        if(printingErrorMessageFlag) System.err.println("User type might entered wrongly, try student/practitioner/lecturer");
                        break;
                }
            }
        }
        return signupStatus;
    }

    /*
     * Method logout is a method that checks if the username is in the connected students list.
     * If the username is in the connected students list, it removes the user from the connected students list.
     * It also prints a goodbye message.
     */
    public  void logout(String username){
        for(User user: this.connectedUsers){
            if(user.getUsername().equals(username)){
                this.connectedUsers.remove(user);
                if(printingMessageFlag) System.out.println("Goodbye " + username + "!");
                return;
            }
        }
    }

    /*
        * Method printStudentCourses is a method that prints the courses of a student.
        * It gets the username.
        * It checks if the user is connected.
        * If the user is connected, it gets the user and prints the courses.
     */
    public void printStudentCourses(String username){
        if(isConnected(username)) {
            User user = getUser(username);
            if (user != null) {
                if(user.getNumOfCourses() == 0) {
                    return;
                }else{
                    user.printMyCourses();
                }
            }
        }
    }

    /*
        * Method printStudentGrade is a method that prints the grade of a student in a course.
        * It gets the course ID and the username.
        * It checks if the user is connected.
        * If the user is connected, it gets the user and prints the grade of the course.
     */
    public void printStudentGrade(String courseID, String username){

        User user = getUser(username);
        if(user == null) {
            if(printingErrorMessageFlag) System.err.println("User does not exist");
        }
        else if (!isConnected(username)) {
            if(printingErrorMessageFlag) System.err.println("User is not connected");
        }
        else if (!(user instanceof Student)) {
            if(printingErrorMessageFlag) System.err.println("User is not a student");
        }
        else if(((Student)user).getNumOfCourses() == 0) {
            if(printingErrorMessageFlag) System.err.println("No courses available");
            return;
        }

        Course course = getCourseByID(courseID);
        if(course == null){
            if(printingErrorMessageFlag) System.err.println("Course does not exist");
        }
        else if(!course.isACourseStudent(username)){
            if(printingErrorMessageFlag) System.err.println("Student is not in the course");
        }
        else {
            ((Student)user).printCourseGrade(course);
        }
    }

    /*
        * Method printStudentGradesAllCourses is a method that prints the grades of a student in all courses.
        * It gets the username.
        * It checks if the user is connected.
        * If the user is connected, it gets the user and prints the grades of all courses.
     */
    public void printStudentGradesAllCourses(String username){
        User user = getUser(username);
        if(user == null) {
            if(printingErrorMessageFlag) System.err.println("User does not exist");
        }
        else if (!isConnected(username)) {
            if(printingErrorMessageFlag) System.err.println("User is not connected");
        }
        else if (!(user instanceof Student)) {
            if(printingErrorMessageFlag) System.err.println("User is not a student");
        }
        else if(((Student)user).getNumOfCourses() == 0) {
            if(printingErrorMessageFlag) System.err.println("No courses available");
            return;
        }else {
            ((Student) user).printAllCourseGrades();
        }
    }

    /*
        * Method printCourseStudents is a method that prints the students of a course.
        * It gets the course ID.
        * It checks if the course exists.
        * If the course exists, it prints the students of the course.
     */
    public void printCourseStudents(String courseID){
        Course course = getCourseByID(courseID);
        if(course != null){
            course.showCourseStudents();
        }else if(printingErrorMessageFlag) System.err.println("Course does not exist");
    }

    /*
        * Method printNotifications is a method that prints the notifications of a user.
        * It gets the username.
        * It checks if the user exists.
        * If the user exists, it prints the notifications of the user.
     */
    public void printNotifications(String username){
        User user = getUser(username);
        if(user != null){
            user.printNotifications();
        }else if(printingErrorMessageFlag) System.err.println("User does not exist");
    }

    /*
        * Method showAllUniversityCourses is a method that shows all the university courses.
        * It gets the courses from the university instance and prints them.
     */
    public void printAllCourses() {
        List<Course> courses = this.universityInstance.getCourses();
        for(Course course: courses){
            if(printingMessageFlag)System.out.println(course.getName() + " - ID: " + course.getID());
        }
    }

    /*
        * Method showSubscribers is a method that shows the subscribers of a course.
        * It gets the course ID.
        * It checks if the course exists.
        * If the course exists, it shows the subscribers of the course.
     */
    public void printSubscribers(String courseID) {
        Course course = getCourseByID(courseID);
        if(course != null){
            course.showSubscribers();
        }else if(printingErrorMessageFlag) System.err.println("Course does not exist");
    }

    /*
        * Method isConnectedByUsername is a method that checks if a user is connected by its username.
        * It gets the username.
        * It returns true if the user is connected, false otherwise.
     */
    public boolean isConnected(String username){
        User user = getUser(username);
        if(user == null){
            if(printingErrorMessageFlag) System.err.println("User does not exist");
            return false;
        }else{
            return this.connectedUsers.contains(user);
        }
    }

    /*
        * Method isCourseFull is a method that checks if a course is full by its ID.
        * It gets the course ID.
        * It returns true if the course is full, false otherwise.
     */
    public boolean isCourseFull(String courseID){
        Course course = getCourseByID(courseID);
        if(course != null) {
            return course.getIsCourseFull();
        } else {
            if(printingErrorMessageFlag) System.err.println("Course does not exist");
            return false;
        }
    }

    /*
        * Method isUsernameInUse is a method that checks if a username is already in use.
        * It gets the username.
        * It returns true if the username is in use, false otherwise.
     */
    private boolean isUsernameInUse(String username){
        List<Lecturer> lecturersInstance = this.universityInstance.getLecturers();
        List<Practitioner> practitionersInstance = this.universityInstance.getPractitioners();
        List<Student> studentInstance = this.universityInstance.getStudents();

        for(Student student: studentInstance){
            if(student.getUsername().equals(username)){
                return true;
            }
        }
        for(Practitioner practitioner: practitionersInstance){
            if(practitioner.getUsername().equals(username)){
                return true;
            }
        }
        for(Lecturer lecturer: lecturersInstance){
            if(lecturer.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

}
