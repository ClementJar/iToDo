This application is a Spring Boot application designed to interact with a Microsoft SQL Server (MSSQL) database.

Important Note:

I encountered challenges running this application on Docker due to MSSQL and its TCP/IP configuration.  To address these issues, please follow these steps for local development outside of Docker.

Prerequisites

1. Git client installed (https://git-scm.com/downloads)
2. Java 17 SDK installed (https://www.oracle.com/java/technologies/downloads/)
3. Microsoft SQL Server instance (local or remote) configured with TCP/IP enabled
4. SQL Server Management Studio (SSMS) (https://learn.microsoft.com/en-us/sql/ssms/download-sql-server-management-studio-ssms?view=sql-server-ver16)

Running the Application

Clone the Repository:

Bash
git clone [https://<your-github-repo-url>.git](https://github.com/ClementJar/iToDo.git)


Bash
mvn clean install

Create the Database Schema:

Open SQL Server Management Studio (SSMS).
Connect to your SQL Server instance.
Execute the dbo.sql script located in the <your-application-directory>/src/main/resources/ . This script will create the necessary tables and structures in your database.
Enable TCP/IP Connections (if necessary):

Note: This step might not be required if TCP/IP is already enabled on your SQL Server instance.

Open SQL Server Configuration Manager.
Expand "SQL Server Network Configuration" and then "Protocols for <your-sql-server-instance-name>".
Right-click "TCP/IP" and select "Enable".
Restart the SQL Server service.

Run the Application:
