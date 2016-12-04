-- Create the new database
DROP DATABASE IF EXISTS footballProject;
CREATE DATABASE footballProject;

-- Select the database
USE footballProject;

CREATE TABLE players(
	playerId		VARCHAR(10) 	PRIMARY KEY,
   	fname			VARCHAR(50)		NOT NULL,
	lname			VARCHAR(50)		NOT NULL,
    team			VARCHAR(3)		NOT NULL
);

CREATE TABLE combineData(
	combineId		VARCHAR(17)		PRIMARY KEY,		
	playerId		VARCHAR(10) 	DEFAULT NULL,
    height			DECIMAL(3,1)	NOT NULL,
    weight			INT				NOT NULL,
    forty			DECIMAL(3,2)	DEFAULT NULL,
    twenty			DECIMAL(3,2)	DEFAULT NULL,
    threecone		DECIMAL(3,2)	DEFAULT NULL,
    vertical		DECIMAL(3,1)	DEFAULT NULL,
    broad			INT				DEFAULT NULL,
    bench			INT				DEFAULT NULL,
    college			VARCHAR(30)		DEFAULT NULL,
    combineYear		INT 			NOT NULL,
	CONSTRAINT playerId_fk_players
	FOREIGN KEY (playerId)
		REFERENCES players(playerId)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE passingInfo(
	passingId		VARCHAR(21)		PRIMARY KEY,
    playerId		VARCHAR(10)		DEFAULT NULL,
    compPerc		DECIMAL(5,2)	NOT NULL,
    yards			INT				NOT NULL,
    touchdowns		INT				NOT NULL,
    interceptions	INT				NOT NULL,
    rating			DECIMAL(4,1)	NOT NULL,
	CONSTRAINT playerId_fk_players_passing
	FOREIGN KEY (playerId)
		REFERENCES players(playerId)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE receivingInfo(
	receivingId		VARCHAR(23)		PRIMARY KEY,
    playerId		VARCHAR(10)		DEFAULT NULL,
    receptions		INT				NOT NULL,
    catchPerc		DECIMAL(5,2)	NOT NULL,
    yards			INT				NOT NULL,
    yardsPerRec		DECIMAL(3,1)	NOT NULL,
    touchdowns		INT				NOT NULL,
    yardsPerGame	DECIMAL(4,1)	NOT NULL,
	CONSTRAINT playerId_fk_players_receiving
	FOREIGN KEY (playerId)
		REFERENCES players(playerId)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE rushingInfo(
	rushingId		VARCHAR(21)		PRIMARY KEY,
    playerId		VARCHAR(10)		DEFAULT NULL,
	yards			INT				NOT NULL,
    touchdowns		INT				NOT NULL,
    longest			INT				NOT NULL,
    yardsPerAttempt	DECIMAL(4,1)	NOT NULL,
    yardsPerGame	DECIMAL(4,1)	NOT NULL,
	CONSTRAINT playerId_fk_players_rushing
	FOREIGN KEY (playerId)
		REFERENCES players(playerId)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);


