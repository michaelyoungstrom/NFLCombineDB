# NFL Combine DB

## Setup

### Dependencies 

- Running MySQL server 
- Maven

### Create create database 

- run the contents of or import MySQL/createFootballProjectDB.sql

This will create the database `footballProject` on which this project depends on

### Update the `DatabaseHelper`

Add the JDBC URL and database credentials to the DatabaseHelper

### How to run this project

cd to the project's directory

From the project's directory:

`$mvn clean install`

That will output `NFLCombineDB.jar` file under the `target` directory

Double click on the .jar file or run 

`$java -jar target/NFLCombineDB`

