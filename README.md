### How to build and run the application. 
- Make sure you have PostgreSQL installed and running.
- Create a new database using the PostgreSQL interactive terminal or a GUI tool like pgAdmin.
    ```
    sudo -u postgres psql
    ```
    ```
    CREATE DATABASE checkout;
    ```
- Update application.properties with your DB user credentials   
- Run Spring Boot application
    ```
    mvn -Dmaven.test.skip=true spring-boot:run 
    ```
    or
    ```
    mvn package
    java -jar ./target/checkout-0.0.1-SNAPSHOT.jar 
    ```
- DB tables are automatically created, data is inserted from import.sql

### How I approached the problem
1. Reading and understanding requirements
2. Generates spring boot project with spring initializr
2. Add modularization with a few separate components: Controller, Service, Dao(Repository)
3. Create and integrate DB (Postgesql)
4. Write components functionality
3. Covered the cost calculation functionality with tests

### If I had more time.
1. Increase code tests coverage
2. Improve code base
