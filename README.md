# E-Learning Platform Backend
A comprehensive backend system built with Spring Boot and Java for managing an e-learning platform. This project facilitates user role identification and permissions, course management, and notifications about course updates and deadlines.

## Features
- **Role-Based Access Control**: Assign and manage user roles with specific permissions to ensure secure and structured access.
- **Course Management**: Create, view, update, and delete courses with a streamlined interface.
- **Enrollment Management**: Handle student enrollments efficiently, allowing for easy tracking and updates.
- **Notification System**: Notify users about course updates, deadlines, and other important information.
- **Real-Time Updates**: Immediate alerts for changes in course status or approaching deadlines.
- **Java Integration**: Ensures robust code structure and object-oriented design.
- **Comprehensive API**: Well-documented API endpoints for seamless integration with other systems.

## Class diagram
![Blank diagram](https://github.com/Iheb-Zenkri/E-Learning-Platforme-SpringBoot/blob/main/Blank%20diagram.png)

## Installation  
Follow these steps to set up and run the project locally :  
### Prerequisites  
Ensure you have the following installed on your machine :  
- [JDK (Java SE Development Kit)](https://www.oracle.com/java/technologies/downloads/#java11?er=221886) (v17+ recommended)  
- [MySQL](https://www.mysql.com/) (Ensure the MySQL server is running)  
- [Maven](https://maven.apache.org) 

### Steps  

1. **Clone the Repository**  
   ```bash  
   git clone https://github.com/Iheb-Zenkri/E-Learning-Platforme-SpringBoot.git
   cd e-learning-platform-backend
   ```
   
2. **Configure the Database**
   - Create a MySQL database named **elearning**.
   - Update the **application.properties** file located in src/main/resources/ with your database credentials:
   ```bash
    spring.datasource.url=jdbc:mysql://localhost:3306/elearning_db
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
   ```
   
3. **Install Dependencies and Build the Project**
  ```bash
      mvn clean install
   ```

4. **Run the application**
  ```bash
     mvn spring-boot:run
   ```

## Usage  
The API is organized into multiple routes for handling different functionalities
## Base URL
     http://localhost:<port>/ #for exemple http://localhost:8000/

## Technologies

- **Backend Framework**: Spring Boot: Simplifies the creation of production-ready applications.
- **Java**: The core language of the project.
- **MySQL**: A relational database for storing course and user information.
- **Spring Data JPA**: Provides a simplified data access layer to interact with the database.
- **Spring Security**: Ensures secure authentication and authorization mechanisms.
- **Hibernate**: An ORM framework for mapping Java objects to database tables.
- **Maven**: A build automation tool used for project management.

## Contributing
1. **Fork the repository:**
  Click on the "Fork" button at the top right of the repository page.

2. **Clone the repository**
   ```bash
    git clone https://github.com/Iheb-Zenkri/E-Learning-Platforme-SpringBoot.git
    cd e-learning-platform-backend  
   ```
   
3. **Create a new branch**
   ```bash
    git checkout -b feature/your-feature-name  
   ```
   
4. **Make your changes**
  Ensure your changes follow the project's coding style and standards.

5. **Commit your changes**
   ```bash
    git add .  
    git commit -m "Add meaningful commit message"   
   ```
   
6. **Push your changes**
   ```bash
    git push origin feature/your-feature-name    
   ```
7. **Create a pull request**
  Go to the repository’s "Pull Requests" tab and submit your PR.
