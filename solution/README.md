README
====
How to run the your solution...

# Install components
# Install gulp and all dependencies
npm install -g gulp
npm install

#Install bower and dependencies - jquery date ui
npm install -g bower
bower install

#Start front end server
gulp

If running using mysql - please create a mysql database called engage_db
Update gateway-service\src\main\resources\config\application-mysql.properties with your value for spring.datasource.username and spring.datasource.password

Prepare Gateway Code
cd gateway-service
mvn clean install

Run Gateway Cdoe - MySql
java -Dspring.profiles.active=mysql -jar target/gateway-service-0.0.1-SNAPSHOT.jar

Run Gateway Cdoe - H2 In Memory database
java -Dspring.profiles.active=h2 -jar target/gateway-service-0.0.1-SNAPSHOT.jar

IMPORTANT
====
To avoid unconcious bias, we aim to have your submission reviewed anonymously by one of our engineering team. Please try and avoid adding personal details to this document such as your name, or using pronouns that might indicate your gender.