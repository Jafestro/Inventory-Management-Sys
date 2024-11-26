## Testing Strategy

### Introduction
This document outlines the testing strategy for the project. It includes the testing approach, testing types, testing tools, and testing schedule.

### Unit Testing
- **Framework**: JUnit
- **Description**: JUnit is used for testing individual methods and classes in isolation to ensure they work as expected.
- **Tools**: JUnit, Mockito

### Integration Testing
- **Framework**: Spring Boot Test
- **Description**: Spring Boot Test is used for testing the integration of multiple components, such as controllers, services, and repositories, to ensure they work together correctly.
- **Tools**: Spring Boot Test, Mockito

### User Acceptance Testing (UAT)
- **Description**: Manual testing by project team members to validate the application against business requirements. Feedback is also gathered from real users to ensure the application meets their needs.
- **Tools**: Manual testing by project team members
- **Tools**: Discord for live testing sessions and reporting bugs. Team members communicated and documented any issues or feedback directly in Discord.

### Tools and Frameworks
- **JUnit**: For unit testing individual methods and classes.
- **Mockito**: For mocking dependencies in unit tests.
- **Spring Boot Test**: For integration testing of Spring Boot applications.
- **Manual Testing**: For user acceptance testing by project team members.