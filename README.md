# Course Management System

This repository implements a **Course Management System** designed to allow lecturers, practitioners, and students to interact with university courses using a well-structured and scalable system. The system utilizes advanced software design principles and patterns to ensure maintainability, performance, and user-friendliness.


## Features
- **Course Management**: Create, delete, and manage courses (e.g., elective, compulsory, seminar).
- **Student Management**: Register/unregister students for courses with limited capacity.
- **Notifications**: Notify students when course spaces become available.
- **User Roles**: Different roles with specific privileges (students, practitioners, lecturers).
- **Simple Interface**: Exposed through a facade to keep interactions straightforward for end-users.


## Design Patterns Used

1. **Singleton**:
   - **Purpose**: Ensures a single instance of core classes like the `University` to maintain centralized data consistency.
   - **Example**: The `CourseSystemFacade` and `University` use this pattern to avoid duplicate instances.

2. **Factory**:
   - **Purpose**: Creates user and course objects without specifying their concrete classes.
   - **Example**: `UserFactory` and `CourseFactory` abstract the creation process, allowing flexible additions to user or course types.

3. **Observer**:
   - **Purpose**: Allows students to subscribe for notifications when a course's availability changes.
   - **Example**: Students are notified when a seat becomes available in a course they are interested in.

4. **Facade**:
   - **Purpose**: Simplifies the interface for interacting with the system.
   - **Example**: The `CourseSystemFacade` provides a straightforward API, abstracting the complex logic of the backend.

5. **Flyweight**:
   - **Purpose**: Optimizes memory by reusing existing course and user data across operations.
   - **Example**: Shared identity and course objects reduce memory overhead when handling multiple instances.


## Code Structure

- **`CourseSystemFacade`**:
  - Acts as the system's facade, handling user login, course creation, student registration, and more. Simplifies interaction for clients while managing internal complexities.

- **Factories**:
  - `UserFactory` creates users (students, practitioners, lecturers).
  - `CourseFactory` generates courses of different types.

- **Observer Integration**:
  - Implements notification logic for students waiting on course availability.

- **Example Usage**:
  - `ExampleUsingCourseSystem.java` provides a practical implementation showcasing how to interact with the system through dialogs and commands.


## Example Usage -  Course Management
```java
public CourseSystemFacade()
public boolean login(String username, String password)
public boolean sign_up(String username, String password, String userType)
public Course setNewCourse(String courseName, String courseID, String courseType, String username)
public boolean setStudentRegistrationToCourse(String courseID, String username)
public boolean setCourseGradeToStudent(String lecturerUsername, String studentUsername, String courseID, int grade)
```

#### User Management 
```java
courseSystem.sign_up("JohnDoe", "password123", "student");
courseSystem.sign_up("ProfSmith", "password456", "lecturer");
courseSystem.login("ProfSmith", "password456");
```

#### Creating a New Course:
```java
courseSystem.setNewCourse("Java Programming", "101", "elective", "AmitL");
```

#### Registering a Student:
```java
courseSystem.setStudentRegistrationToCourse("101", "AmitS1");
```

#### Notifications for Full Courses:
```java
courseSystem.setChangeSubscriptionCourse("101", "AmitS1");
```

## Running the System

1. Clone the repository:
   ```bash
   git clone https://github.com/AmitGini/Courses-Management.git
   cd Courses-Management
   ```

2. Compile and run:
   ```bash
   javac *.java
   java ExampleUsingCourseSystem
   ```

3. Interact through the terminal prompts to create users, manage courses, and test the system.

---

## Acknowledgments

This project is a showcase of modern OOP and design principles, providing a robust foundation for real-world applications. It demonstrates a blend of simplicity and power, enabling efficient management of university courses.

## System Requirements

- Java 8 or higher
- Command-line interface support

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)

