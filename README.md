# Library Management System Project - Admin

## Description

This project is a Library Management System developed with Java EE, JSP, and Servlets, using SQL Server for database management. Currently implemented for admin roles, it features book search and pagination, with potential for further development in the future.

## Prerequisites

This project requires the following libraries:

- **jTDS**: For connecting to SQL Server.
  - [Download jTDS](http://jtds.sourceforge.net/)
- **jBCrypt**: For hashing passwords.
  - [Download jBCrypt](https://github.com/jeremyh/jBCrypt)

## Installation Instructions

1. **Download the Required Libraries**:

   - Download `jtds-x.x.jar` and `jbcrypt-x.x.jar` from the provided links.

2. **Add Libraries to Your Project**:

   - Copy the downloaded JAR files to the `WEB-INF/lib` directory of your project.

3. **Deploy the Application**:

   - If you are using a servlet container like Apache Tomcat, deploy the project as a WAR file or copy the project folder to the webapps directory.

4. **Run the Application**:
   - Start your server and access the application at `http://localhost:8080/FinanceManager`.

## Important

The project does not include JAR files in the repository. Please ensure you download and add the required libraries as mentioned in the Prerequisites section.
