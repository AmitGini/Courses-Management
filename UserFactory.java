public class UserFactory {
    public static User createUser(String username, String password, String userType){
        String userTypeLowerCase = userType.toLowerCase();
        if (userTypeLowerCase.equals("student")){
            return new Student(username, password);
        } else if (userType.equals("practitioner")){
            return new Practitioner(username, password);
        } else if (userType.equals("lecturer")){
            return new Lecturer(username, password);
        } else {
            return null;
        }
    }
}
