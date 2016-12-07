# NFL Combine DB

## Setup

### Dependencies 

- Running MySQL server 
- Maven

### Update the `DatabaseHelper`

Add the JDBC URL and database credentials to the DatabaseHelper

### How to run this project

cd to the project's directory

From the project's directory:

`$mvn clean compile assembly:single`

That will output a `.jar` file under the `target` directory

Double click on the .jar file or run 

`$java -jar `

