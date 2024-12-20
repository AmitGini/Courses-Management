# Course Management System

A Java-based university course management system that implements various design patterns and object-oriented principles to provide a robust and user-friendly interface for course registration and management.

## Features

- User Management
  - Support for multiple user types (Students, Lecturers, Practitioners)
  - Default capacity of 100 concurrent active users (configurable)
  - Secure login and signup functionality

- Course Management
  - Creation of different course types (Seminar, Elective, Mandatory)
  - Course capacity management
  - Grade management
  - Course registration and unregistration
  - Notification system for course availability

- User Interface
  - Simple command-line interface
  - Clear menu-driven navigation
  - Role-based access control

## Design Patterns Implementation

### 1. Singleton Pattern
- Implemented in the University class
- Ensures only one instance of the university system exists
- Centralizes course and user data management
- Prevents data inconsistencies from multiple instances

### 2. Factory Pattern
- Implemented through UserFactory and CourseFactory
- Creates different types of users (Student, Lecturer, Practitioner)
- Creates different types of courses (Seminar, Elective, Mandatory)
- Enables easy extension for new user or course types

### 3. Observer Pattern
- Implemented in the course notification system
- Students can subscribe to full courses
- Automatic notifications when spots become available
- Enables future expansion for additional notification types

### 4. Facade Pattern
- Implemented through CourseSystemFacade
- Provides simplified interface to complex subsystems
- Handles:
  - User authentication
  - Course registration
  - Grade management
  - Notifications
- Hides implementation complexity from end users

### 5. Flyweight Pattern
- Used for managing user identities in courses
- Shares common user data across multiple courses
- Optimizes memory usage
- Improves system performance with large user bases

## Code Structure

### Main Components

1. `CourseSystemFacade.java`
   - Central interface for all system operations
   - Manages user sessions and system capacity
   - Handles course and user operations
   - Implements error handling and status messages

2. `ExampleUsingCourseSystem.java`
   - Demonstrates system usage
   - Provides command-line interface
   - Implements menu-driven user interaction
   - Shows example workflows for different user types

### Key Features Implementation

#### User Management
```java
public boolean login(String username, String password)
public boolean sign_up(String username, String password, String userType)
```

#### Course Management
```java
public Course setNewCourse(String courseName, String courseID, String courseType, String username)
public boolean setStudentRegistrationToCourse(String courseID, String username)
public boolean setCourseGradeToStudent(String lecturerUsername, String studentUsername, String courseID, int grade)
```

## Usage Example

```java
CourseSystemFacade courseSystem = new CourseSystemFacade();

// System configuration
courseSystem.setSystemCapacity(99); // Change default capacity from 100
courseSystem.setStatusPrintingMessageFlag(true);

// User creation
courseSystem.sign_up("JohnDoe", "password123", "student");
courseSystem.sign_up("ProfSmith", "password456", "lecturer");

// Course management
courseSystem.login("ProfSmith", "password456");
courseSystem.setNewCourse("Java Programming", "CS101", "elective", "ProfSmith");
```

## System Requirements

- Java 8 or higher
- Command-line interface support


## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)

Feel free to send improvements or suggest interesting features
