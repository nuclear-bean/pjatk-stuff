-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-12-30 11:33:28.298

-- foreign keys
ALTER TABLE Encounter
    DROP FOREIGN KEY Encounter_Camera;

ALTER TABLE Encounter
    DROP FOREIGN KEY Encounter_Car;

-- tables
DROP TABLE Camera;

DROP TABLE Car;

DROP TABLE Encounter;

-- End of file.


