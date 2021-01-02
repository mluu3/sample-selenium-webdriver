# Welcome to Arquillian Test Sample Project

This maven project consists of following technologies/frameworks:
 - TestNG
 - Arquillian (testng-standalone)
 - Graphene/Drone
 - Selenium
This combination allows you to easily write UI and API tests in Java.

## Build
### Local build
Build can be triggered from any graphene-test folder with the following command

```
mvn clean install -Pselenium
```
Or you can run all tests together from parent project.


### Running single test
Specify test you want to run by test parameter.
For firefox: 
-Dtest.execution.env=local-firefox
-DfirefoxDriverBinaryPath=<path_geckodriver>

For chrome: 
-Dtest.execution.env=local-chrome 
-DchromeDriverBinaryPath=<path_chromedriver>
```
mvn -DfirefoxDriverBinaryPath=/Users/minhluu/Documents/browser_driver/geckodriver -Dtest=SearchTest integration-test -Pselenium
mvn -Dtest.execution.env=local-chrome -Dtest=UserTest integration-test -Pselenium
```
Please note test suite name needs to be suffixed by Test not Tests. Otherwise it's not possible to run it separately.

### Debugging
Run test with Surefire plugin debug option.

```
mvn -Dmaven.surefire.debug integration-test -Pselenium
```
Test execution should wait for attaching debugger to 5005 port. See example configuration of maven-debug inside IntelliJ IDEA.

## Note:
First time, need to download and install some drivers as browser, maybe failed or unstable. Please re-run. 