----------
Wired Brain Coffee API
===================  
This application is an implementation of Spring Boot's non-blocking WebFlux. It provides endpoints for all CRUD actions necessary for the hypothetical "Wired Brain Coffee" company.

----------
## Required Dependencies

### Java 8

Spring Boot 2.0 and beyond requires Java 8 as a minimum version. See [this link](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Release-Notes#java-8-baseline-and-java-9-support) for more details. You can download the latest version of Java [here](http://www.oracle.com/technetwork/java/javase/overview/index.html).

### Lombok

This application utilizes Lombok for automatic getter/setter generation and more. If using STS, you must install this separately. See Lombok's documentation [here](https://projectlombok.org/features/all).

##### Install with STS
1. Navigate to https://projectlombok.org/
1. Click **Download**, and download the latest version.
1. Open terminal, and navigate to the directory that you downloaded the lombok jar to.
1. Run the following command:
    * `sudo java -jar lombok.jar`
    * **Note:** On a Windows PC, you can probably just double-click the jar from Windows Explorer. On a Mac, you need to use terminal.

##### Install with IntelliJ IDEA
1. Within IntelliJ, go to **Preferences** > **Plugins**
1. Click **Browse Repositories**
1. Search for "Lombok"
1. Select the **Lombok Plugin** and click the **Install** button.
1. Once complete, click the button to restart IntelliJ IDEA.

----------
## Running the Project

#### From the command line
1. Open a new terminal window and navigate to the project root.
1. Execute the command: `gradle bootRun`.
1. Confirm that the application is running by navigating to `http://localhost:8080` in a browser.

#### From an IDE
1. Open the project in your favorite IDE.
1. Click "Run" (in STS it will be "Run as Spring Boot Application").
1. Confirm that the application is running by navigating to `http://localhost:8080` in a browser.

----------
## Tools and Technologies
#### Core
- **Spring Boot 2.0.2** - First-class support for reactive applications, see the [docs](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/).

#### Testing
- **JUnit 4.12** - Unit Testing, see the [docs](https://junit.org/junit4/).
