# Boilerplate_TestNG

Basic codes for using testNg with maven

It uses Extent Report to generate reports

Maven surefire plugin is used for running tests from terminal

### Maven commands to run tests

#### To clean the project

**mvn clean**

#### TO run test

**mvn compile**

**mvn test** 
(_Though running mvn test will compile too_)

#### To ignore the failed tests

**mvn -Dmaven.test.failure.ignore=true test**
