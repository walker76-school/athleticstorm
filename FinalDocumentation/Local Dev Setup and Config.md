# **Local Dev Setup and Configuration Instructions**

This guide will walk you through setting up and deploying the Athletic Storm application. You can either install and configure the application technologies manually or utilize [Docker](https://www.docker.com/) for easy deployment.

# **Manual Intallation Instructions**

The Athletic Storm application runs using [MySQL](https://www.mysql.com/) for the database, [Spring Boot](https://spring.io/projects/spring-boot) for the API and [ReactJS](https://reactjs.org/) for the front-end.

**MySQL**

First you will need to install the MySQL server for your computer. Once it is installed, you will need to first create a schema and user for the Athletic Storm application using the script below:

> CREATE SCHEMA &#39;athleticstorm&#39;;
>
> CREATE USER &#39;athleticstorm&#39;@&#39;localhost&#39; IDENTIFIED WITH mysql_native_password BY &#39;mysqlpazz&#39;;
>
> GRANT ALL PRIVILEGES ON athleticstorm.\* TO &#39;athleticstorm&#39;@&#39;localhost&#39;

_Note: According to various formats and configurations of MySQL, the apostrophe character ( __&#39;__ ) might not be needed surrounding certain keywords. Look to the documentation for your specific version of MySQL. If an error occurs, you might need to drop the schema and start over again (from creating the schema) to clear the error._

**Spring Boot**

To run the API, you will need to have the MySQL server running and configured using the steps above. The Java version Athletic Storm was designed on is [Java 8.](https://openjdk.java.net/install/) You will also need to have [Maven](https://maven.apache.org/index.html) installed to properly run this application. Navigate to the root folder of the back-end and execute the Maven build commands to build and test the application.

>mvn clean install

>mvn spring-boot:run

After doing this, you are able to run Athletic Storm from the command line.

**Front-End**

The last thing to deploy Athletic Storm is the front-end application. You will need to install [NodeJS](https://nodejs.org/en/). After it is properly installed, navigate to the root of the front-end application and execute the NodeJS commands below to build and run the application:

>npm install

>npm start

Once the application is running using the above commands, you are able to access it at [localhost:3000](https://localhost:3000/) in a web browser.

# **Docker Instructions**

Instead of using the manual configuration as detailed above, you are able to use Docker.

First, you will need to install [Docker](https://www.docker.com/) and [Docker Compose.](https://docs.docker.com/compose/) Once you have both of those installed, you can run the application from the root of the entire project using the following command in the command line:

>docker-compose build

>docker-compose up