README
====

# Components
1. Java 8
2. Spring Boot 1.5.10.RELEASE
3. Mysql / H2 DB

How to run the your solution...

# Install components
### Install gulp and all dependencies
npm install -g gulp

npm install

### Install bower and dependencies - jquery date ui
npm install -g bower

bower install

### Start front end server
gulp

## If Using Mysql
If running using mysql - please create a mysql database called engage_db
Update gateway-service\src\main\resources\config\application-mysql.properties with your value for spring.datasource.username and spring.datasource.password

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