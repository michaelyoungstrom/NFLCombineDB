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

## GUI Instructions

All of the CRUD opeartions are supported by this application.

To read player statistics from the database:

- Type in the desired player's name and click Search
- Select the player from the results list
- Click Stats to read existing statistics in the database

To add a player to the database:

- Click the Add button
- If prompted with options, click the Player button
- Enter the desired player information and click Save

To add new statistics to the database:

- Type in the desired player's name and click Search
- Select the player from the results list
- Click the Update button
- Select which table you would like to add
- Enter the desired data and click the Ok button

To update the database:

- Type in the desired player's name and click Search
- Select the player from the results list
- Click the Update button
- Select which table you would like to update
- Enter the changes and click the Ok button

To delete a player from the database:

- Type in the desired player's name and click Search
- Select the player from the results list
- Click the Delete Player button
- Click the Yes button





