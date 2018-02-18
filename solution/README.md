README
====

# Components
1. Java 8
2. Spring Boot 1.5.10.RELEASE
3. Mysql / H2 DB

# Project Setup
This project uses Zuul in the gateway service to filter request to the correct microservice. The Gateway service validates that the user has a correct api key and stops the request if not.
 
This gateway can be expanded in the future by adding more microservices as well as adding additional operations to be performed on the request and response messages.

# How to run the your solution...

# Install components
### Install gulp and all dependencies (from the parent project directory)
npm install -g gulp

npm install

### Install bower and dependencies - jquery date ui (from the parent project directory)
npm install -g bower

bower install

### Start front end server (from the parent project directory)
gulp

## If Using Mysql
If running using mysql - please create a mysql database called engage_db

Update gateway-service\src\main\resources\config\application-mysql.properties and expense-service\src\main\resources\config\application-mysql.properties with your value for spring.datasource.username and spring.datasource.password

### 1. Run Expense Service
cd expense-service

mvn clean install

java -Dspring.profiles.active=mysql -jar target/expense-service-0.0.1-SNAPSHOT.jar

### 2. Run Gateway Service
cd gateway-service

mvn clean install

java -Dspring.profiles.active=mysql -jar target/gateway-service-0.0.1-SNAPSHOT.jar


## If Using In Memory (h2 database)
### 1. Run Expense Service
cd expense-service

mvn clean install

java -Dspring.profiles.active=h2 -jar target/expense-service-0.0.1-SNAPSHOT.jar

### 2. Run Gateway Service
cd gateway-service

mvn clean install

java -Dspring.profiles.active=h2 -jar target/gateway-service-0.0.1-SNAPSHOT.jar


# Run Tests and View Code Coverage Results
### Expense Service
cd expense-service

mvn clean clover:setup test clover:aggregate clover:clover -Dsurefire.skip.tests=false;

In chrome - open the file target\site\clover\index.html

### Gateway Service
cd gateway-service

mvn clean clover:setup test clover:aggregate clover:clover -Dsurefire.skip.tests=false;

In chrome - open the file target\site\clover\index.html