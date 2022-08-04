-- car

INSERT INTO Car (registration,type,color) 
VALUES 
("WX8282Z","Osobowy","Czarny"), 
("WB12CD","Motocykl",NULL), 
("AB404D",NULL,NULL), 
("DX1234H","Osobowy","Biały"), 
("WD1212J","Ciężarowy","Biały"); 

-- camera
INSERT INTO Camera (alias,location,manufacturer,resolution)
VALUES
("Brama Gl.","Budynek A","Hikvision","1080p"),
("Klatka sch.","Budynek A","Hikvision","720p"),
("Magazyn","Budynek B",NULL,"1080p"),
("Parking","Budynek C","DVS Polska",NULL);

-- encounter
INSERT INTO Encounter (Car_registration,Camera_id,time,authorized,direction)
VALUES
("WX8282Z",1,"2020-10-12 11:30:00",true,false),
("DX1234H",4,"2020-10-12 12:15:20",NULL,false),
("DX1234H",3,"2020-10-12 12:16:00",NULL,false),
("DX1234H",4,"2020-10-12 12:16:45",NULL,true),
("WD1212J",1,"2020-10-12 13:15:00",true,true),
("WD1212J",1,"2020-10-12 13:20:00",true,false);


