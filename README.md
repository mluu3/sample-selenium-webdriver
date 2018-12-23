# Welcome to Arquillian Test Sample Project

This maven project consists of following technologies/frameworks:
 - TestNG
 - Arquillian (testng-standalone)
 - Graphene/Drone
 - Selenium
This combination allows you to easily write UI tests in Java.

## Build
### Local build
Build can be triggered from any graphene-test folder with the following command

```
mvn clean install
```
Or you can run all tests together from parent project.

### Running suite
```
mvn -Dtest.suite=testng-user-test.xml
```

### Running single test suite
Specify test suite you want to run by test parameter.

```
mvn -DselectedTests=Login-Test test
```
Please note test suite name needs to be suffixed by Test not Tests. Otherwise it's not possible to run it separately.

### Debugging
Run test with Surefire plugin debug option.

```
mvn -Dmaven.surefire.debug test
```
Test execution should wait for attaching debugger to 5005 port. See example configuration of maven-debug inside IntelliJ IDEA.

## Note:
Random email is used to create new customer, so that, test is sometimes failed by existed email. Please re-run.
