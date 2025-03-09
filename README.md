

## Attendance Monitoring System



The Attendance Management System is a Java-based desktop application designed for efficiently managing attendance records of students or employees. The system is built using **Java Swing** for an interactive 
**graphical user interface (GUI)** and **JDBC** for database operations, with **MySQL** serving as the backend database. The project is developed and executed in **Visual Studio Code (VS Code).**

**This system allows administrators to manage user data, mark attendance, view reports, and store attendance records in a structured manner.**


---

**Key Features**


**✔ Student/Employee Management** – Add, update, and delete records.


**✔ Database Integration** – MySQL connectivity using JDBC.


**✔ User-Friendly GUI** – Built with Java Swing for seamless interaction.




---

**Technologies Used** 

 **1.  JAVA (JDK 8 or above)** - Core programming language. 
 
 **2. Swing (Java.swing)** - GUI Development.
 
 **3. JDBC (Java Database Connectivity)** - Database Connectivity.
 
 **4. MySQL** - Database for storing attendance records.
 
 **5. Visual Studio Code (VS Code)** - Development environment (IDE).
 
 **6. MySQL Connector JAR**  - JDBC driver for database connection. 

---

**System Requirements**

Before running the project, we ensure that the following dependencies and tools are installed :

**Java Development Kit (JDK) 8 or above**

**Visual Studio Code (VS Code) with Java Extensions**

**MySQL Server & Workbench**

**MySQL Connector JAR**



---

**Installation & Setup Guide**

Step 1 : **Configure the Database**

1. Start MySQL Server and open MySQL Workbench or any SQL client.


2. **Create the database by executing:**

     CREATE DATABASE attendance_db;  

     USE attendance_db;


4. **Create the table structure for attendance records:**

    drop database attendance_db;

    create database  attendance_db;

    use attendance_db;

    create table attendance (
	
    id INT PRIMARY KEY AUTO_INCREMENT,
    
    student_name VARCHAR(50) NOT NULL,
    
    subject VARCHAR(50) NOT NULL,
    
    total_classes INT NOT NULL,
    
    classes_attended INT NOT NULL,
    
    total_attendance VARCHAR(10) NOT NULL );



Step 2 : **Configure the Database Connection in Java**

    Update the database connection settings in the DatabaseConnection.java file:

    package com.attendance;

    import java.sql.Connection;

    import java.sql.DriverManager;

    import java.sql.SQLException;

    public class DatabaseConnect {
    
    private static final String URL = "jdbc:mysql://localhost:3306/attendance_db";
   
    private static final String USER = "root";
    
    private static final String PASSWORD = "bilalabdullah@sadaf";

    public static Connection getConnection() {
       
        Connection conn = null;
        
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load JDBC Driver
            
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            System.out.println("Database Connected Successfully!");
        } 
            catch (ClassNotFoundException e) {
            
            System.out.println("JDBC Driver Not Found.");
            
            e.printStackTrace();
        }   
            catch (SQLException e) {
            
            System.out.println("Database Connection Failed.");
            
            e.printStackTrace();
        }
        
        return conn;
    }
}



Step 3 : **Run the Project in VS Code**

1. Open the Attendance Management System project in VS Code.


2. Ensure that all required dependencies (JAR files) are added to the project.


3. Compile and run the main Java file:

     javac AttendanceSystem.java  

     java AttendanceSystem (Can be run through the main method directly too)


---

**Future Enhancements**

🔹 Role-Based Access Control (RBAC) – Different user roles (Admin, Teacher, Student).

🔹 Export Data – Ability to export reports in Excel or PDF format.

🔹 Automated Email Notifications – Send attendance reports via email.

🔹 Graphical Analytics – Represent attendance data using graphs and charts.

🔹 Mobile App Integration – Extend functionality to a mobile platform using SpringBoot. 


---

**Best Practices Followed**

✅ Object-Oriented Programming (OOP) – Modular and reusable code structure.

✅ MVC Architecture – Separation of UI, business logic, and database operations.

✅ Error Handling – Exception management for database and UI operations.

✅ Scalability & Maintainability – Designed to accommodate future enhancements.


---
