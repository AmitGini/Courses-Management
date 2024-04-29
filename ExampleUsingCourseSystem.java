import java.util.Scanner;

public class ExampleUsingCourseSystem {

    public static CourseSystemFacade courseSystem = new CourseSystemFacade(); // Singleton Pattern
    public static Scanner scan = new Scanner(System.in);


    public static void main(String[] args) {

        courseSystem.setStatusPrintingMessageFlag(true); // default false
        courseSystem.setStatusPrintingErrorMessageFlag(true); //default false
        courseSystem.setSystemCapacity(99); //default 100
        courseSystem.sign_up("AmitS1", "1", "student");
        courseSystem.sign_up("AmitS2", "1", "student");
        courseSystem.sign_up("AmitS3", "1", "student");
        courseSystem.sign_up("AmitP", "1", "practitioner");
        courseSystem.sign_up("AmitL", "1", "lecturer");
        courseSystem.login("AmitS1", "1");
        courseSystem.login("AmitS2", "1");
        courseSystem.login("AmitP", "1");
        courseSystem.login("AmitL", "1");
        courseSystem.setNewCourse("Java", "1", "elective", "AmitL");
        courseSystem.setCourseCapacity("1", "AmitL",2);
        courseSystem.setLecturerPractitionerToCourse("1", "AmitS1");
        courseSystem.setLecturerPractitionerToCourse("1", "AmitS2");
        courseSystem.setLecturerPractitionerToCourse("1", "AmitS3");
        courseSystem.setLecturerPractitionerToCourse("1", "AmitP");
        courseSystem.setCourseGradeToStudent("AmitL", "AmitS1", "1", 90);
        courseSystem.setChangeSubscriptionCourse("1", "AmitS3");
        courseSystem.setStudentUnRegistrationToCourse("1", "AmitS2");
        courseSystem.setLecturerPractitionerToCourse("1", "AmitS3");
        courseSystem.setCourseGradeToStudent("AmitL", "AmitS3", "1", 70);
        startDialog();
    }


    public static void startDialog(){
        while(true) {
            System.out.println("\nWelcome to the Course Management System");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String input = scan.nextLine();
            switch (input) {

                case "1":
                    LoginDialog();
                    break;

                case "2":
                    signUpDialog();
                    break;

                case "0":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private static void LoginDialog() {
        System.out.println("\n*** Login Screen: ***");
        System.out.print("Username: ");
        String username = scan.nextLine();
        System.out.print("Password: ");
        String password = scan.nextLine();
        if(courseSystem.login(username, password)) {
            userDialog(username);
        }
    }

    private static void signUpDialog() {
        System.out.println("\n*** Signup Screen: ***");
        System.out.println("*** Note! in the system you can signup as student/practitioner/lecturer ***");
        System.out.println("*** We can add Approval account by the university, but for assignment will keep it as is. ***");

        System.out.print("Username: ");
        String username = scan.nextLine();
        System.out.print("Password: ");
        String password = scan.nextLine();
        System.out.print("User Type (student, practitioner, lecturer): ");
        String userType = scan.nextLine();

        courseSystem.sign_up(username, password, userType);
    }

    private static void userDialog(String username) {
        while(true) {
            System.out.println("\n**** Welcome "+ username +" ****");
            boolean isStudent = courseSystem.getUser(username) instanceof Student;
            System.out.println("1. Show my courses");

            if(isStudent){
                System.out.println("2. Register to a course");
                System.out.println("3. Show All My Grades");
            }
            else{
                System.out.println("2. Create a course");
                System.out.println("3. Delete a course");
            }

            System.out.println("4. Notifications");
            System.out.println("0. Log out");
            System.out.print("Enter your choice: ");

            String input = scan.nextLine();
            switch (input) {

                case "1":
                    coursesDialog(username);
                    break;

                case "2":
                    if(isStudent){
                        courseRegistrationDialog(username);}
                    else{
                        courseCreationDialog(username);}
                    break;

                case "3":
                    if(isStudent) {
                        courseSystem.printStudentGradesAllCourses(username);
                    }
                    else{
                        courseDeletionDialog(username);}
                    break;

                case "4":
                    courseSystem.printNotifications(username);
                    break;

                case "0":
                    courseSystem.logout(username);
                    return;

                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }


    // -1 = invalid course | 0 = no available | 1 = success registration
    private static void courseRegistrationDialog(String username) {
        while (true) {

            System.out.println("\n**** Register to a course ****");
            courseSystem.printAllCourses();

            System.out.print("Enter the course ID: ");
            String courseID = scan.nextLine();
            if(courseSystem.getCourseByID(courseID) == null) {
                System.out.println("Course does not exist");
            } else if(courseSystem.isCourseFull(courseID)){
                System.out.println("Course is full. Registration failed.");
                System.out.println("Would you like to get Notified when the course is available? (yes/no)");

                System.out.print("Enter your choice: ");
                String input = scan.nextLine();
                input = input.toLowerCase();

                if (input.equals("yes")) {
                    courseSystem.setChangeSubscriptionCourse(courseID, username);
                    return;
                } else if (input.equals("no") || input.equals("back")){
                    return;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            }else{
                courseSystem.setStudentRegistrationToCourse(courseID, username);
                //System.out.println("Successfully registered to " + courseSystem.getCourseNameByID(courseID) + " - " + courseID);
                return;
            }

        }
    }

    private static void courseUnregistrationDialog(String username, String courseID) {
        String courseName = courseSystem.getCourseNameByID(courseID);
        if(courseName != null){
            if(courseSystem.setStudentRegistrationToCourse(courseID,username)) {
                System.out.println("Successfully unregistered from " + courseName + " - " + courseID);
            }else {
                System.out.println("Failed to unregister from " + courseName + " - " + courseID);
            }
        }else{
            System.out.println("Course does not exist");
        }
    }

    private static void courseCreationDialog(String username) {
        System.out.println("\n**** Create a course ****");
        System.out.print("Enter the course name: ");
        String courseName = scan.nextLine();
        System.out.print("Enter the course ID: ");
        String courseID = scan.nextLine();
        System.out.print("Enter the course type (elective, compulsory, seminar): ");
        String courseType = scan.nextLine();
        courseSystem.setNewCourse(courseName, courseID, courseType, username);
    }

    private static void courseDeletionDialog(String username) {
        System.out.println("\n**** Delete a course ****");
        courseSystem.printStudentCourses(username);
        System.out.print("Enter the course ID: ");
        String courseID = scan.nextLine();
        courseSystem.setRemoveCourse(courseID, username);
    }

    private static void coursesDialog(String username) {

        while(true) {

            System.out.println("\n**** Courses for " + username + " ****");
            courseSystem.printStudentCourses(username);

            System.out.print("\nEnter the course ID Or Back: ");
            String courseID = scan.nextLine();
            if(courseID.equals("Back") || courseID.equals("back")) {
                return;
            }

            if(courseSystem.getCourseByID(courseID) != null) {

                boolean isStudent = courseSystem.getUser(username) instanceof Student;

                if (!isStudent) {
                    System.out.println(("1. Enter Course Grades"));
                    System.out.println("2. Show Course Students");
                    System.out.println("3. Show Subscribers");
                    System.out.println("4. Add Lecturer/Practitioner");
                }else{
                    System.out.println("1. Show Course Grade");
                    System.out.println("2. Unregister from a course");
                    System.out.println("3. Unsubscribe course");
                }
                System.out.println("0. Back");
                System.out.print("Enter your choice: ");

                String input = scan.nextLine();
                switch (input) {
                    case "1":
                        if (!isStudent) {
                            enterCourseGradesDialog(courseID, username);
                        }else{
                            courseSystem.printStudentGrade(courseID,username);
                        }
                        break;

                    case "2":
                        if (!isStudent) {
                            courseSystem.printCourseStudents(courseID);
                        }else{
                            courseUnregistrationDialog(username, courseID);
                        }
                        break;

                    case "3":
                        if (!isStudent) {
                            courseSystem.printSubscribers(courseID);
                        }else{
                            courseSystem.setChangeSubscriptionCourse(courseID, username);
                        }
                        break;

                    case "4":
                        if(!isStudent){
                            System.out.print("Enter the username: ");
                            String teacherUsername = scan.nextLine();
                            courseSystem.setLecturerPractitionerToCourse(courseID, teacherUsername);
                        }
                        break;

                    case "0":
                        return;

                    default:
                        System.out.println("Invalid input. Please try again.");
                }
            }else{
                System.out.println("Course does not exist");
            }
        }
    }

    private static void enterCourseGradesDialog(String courseID, String teacherUsername) {

        if(courseSystem.getCourseByID(courseID) != null) {
            System.out.println("\n**** Course Grades Screen ****");

            while(true) {

                courseSystem.printCourseStudents(courseID);
                System.out.print("Enter the student username Or Write Back: ");
                String studentUsername = scan.nextLine();

                if(studentUsername.equals("Back") || studentUsername.equals("back")) {
                    return;
                }
                else{
                    System.out.print("Enter the student grade: ");
                    String studentGrade = scan.nextLine();

                    try{
                        int grade = Integer.parseInt(studentGrade);
                        courseSystem.setCourseGradeToStudent(teacherUsername , studentUsername, courseID, grade );
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Invalid grade. Please try again.");
                        return;
                    }
                }
            }
        }else{
            System.out.println("Course does not exist");
        }
    }


}