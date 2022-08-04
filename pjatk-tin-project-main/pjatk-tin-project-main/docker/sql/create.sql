-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-12-30 11:44:58.231

-- tables
-- Table: Camera
CREATE TABLE Camera (
    id int NOT NULL AUTO_INCREMENT,
    alias varchar(255) NOT NULL,
    location varchar(255) NOT NULL,
    manufacturer varchar(255) NULL,
    resolution varchar(255) NULL,
    CONSTRAINT Camera_pk PRIMARY KEY (id)
);

-- Table: Car
CREATE TABLE Car (
    registration varchar(8) NOT NULL,
    type varchar(255) NULL,
    color varchar(255) NULL,
    CONSTRAINT Car_pk PRIMARY KEY (registration)
);

-- Table: Encounter
CREATE TABLE Encounter (
    id int NOT NULL AUTO_INCREMENT,
    Car_registration varchar(8) NOT NULL,
    Camera_id int NOT NULL,
    time timestamp NOT NULL,
    authorized bool NULL,
    direction char(2) NULL,
    CONSTRAINT Encounter_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: Encounter_Camera (table: Encounter)
ALTER TABLE Encounter ADD CONSTRAINT Encounter_Camera FOREIGN KEY Encounter_Camera (Camera_id)
    REFERENCES Camera (id);

-- Reference: Encounter_Car (table: Encounter)
ALTER TABLE Encounter ADD CONSTRAINT Encounter_Car FOREIGN KEY Encounter_Car (Car_registration)
    REFERENCES Car (registration);

-- End of file.


