-- create default user

INSERT INTO user (login,pass) VALUES ("mas","86a59e0fdad79f598dfff21f63b4621adeaba9f96be6ed0df3c0f6178ed2e581");

-- populate client
INSERT INTO Klient (imie,nazwisko,data_ur,numer,data_rejestracji) VALUES
("Jan","Mortadela","1998-05-20",400500600,"2019-08-08"),
("Monika","Łyżka","1983-03-12",123456789,"2020-02-18"),
("Elżbieta","Mortadela","1997-03-22",500800400,"2018-01-02"),
("Krzysztof","Myszkowski","1996-01-31",999666888,"2021-01-13"),
("Jean","Francois","1987-04-20",200300600,"2020-03-15"),
("Doge","Snopp","1985-11-12",505505505,"2019-04-22"),
("Katarzyna","Piątek","1999-11-17",987654321,"2017-09-21"),
("Lazar","Edelenau","1996-09-02",256341987,"2017-09-18"),
("Albert","Niemiec","2002-12-18",454456123,"2017-11-03"),
("Weronika","Kostrzewska","1973-01-02",415263789,"2019-12-28"),
("Maciej","Musiał","1998-05-20",699366255,"2020-05-08"),
("Krytyna","Krystyńska","1984-04-01",799466133,"2019-03-09"),
("Adam","Malik","2000-01-20",566899455,"2019-09-01"),
("Diego","Azevado","1989-04-22",122455788,"2019-12-25"),
("Iwona","Opolska","1996-03-04",366588144,"2019-10-25"),
("Małgorzata","Thatcher","2001-02-24",824697135,"2019-10-11"),
("Krystian","Kłoda","1993-11-09",369852147,"2020-01-15"),
("Michał","Drewno","1968-09-14",791364825,"2020-02-03"),
("Janina","Staropolska","1991-07-07",391758642,"2017-03-18"),
("Wiktor","Elokwentny","1997-12-04",938265714,"2018-12-26")

-- populate trainer
INSERT INTO Trener (imie,nazwisko,data_ur,numer,poziom,opis) VALUES
("Paweł","Jankowski","1985-03-16",523619478,"DZIECI,POCZATKUJACY","Jestem Paweł i nauczę Cię grać w tenisa"),
("Aleksandra","Drożdż","1989-12-03",741852363,"POCZATKUJACY,SREDNIOZAAWANSOWANY","Jestem Ola i szybko biegam"),
("Mateusz","Dziubek","1985-03-16",523619478,"ZAAWANSOWANY","Jestem Mateusz i uczę grać profesjonalistów"),
("Francois","De la France","1979-04-20",593826471,"SREDNIOZAAWANSOWANY, ZAAWANSOWANY","Je parle francais"),
("Katarzyna","Wciśło","1992-10-01",7485321369,"DZIECI,POCZATKUJACY,SREDNIOZAAWANSOWANY","Jestem Kasia"),

-- populate court
INSERT INTO Kort (nawierzchnia,cena,oswietlenie,kryty) VALUES 
("MACZKA",150,1,1),
("MACZKA",150,1,1),
("MACZKA",150,0,1),
("MACZKA",150,0,1),
("MACZKA",150,0,1),
("MACZKA",150,1,0),
("MACZKA",150,1,0),
("MACZKA",150,1,0),
("MACZKA",150,0,0),
("MACZKA",150,0,0),
("MACZKA",150,0,0),
("LINOLEUM",170,1,1),
("LINOLEUM",170,0,1),
("LINOLEUM",170,0,1),
("LINOLEUM",200,1,0),
("LINOLEUM",200,1,0),
("LINOLEUM",200,1,1),
("TRAWA",250,1,0),
("TRAWA",250,1,0),
("TRAWA",250,1,0)

-- populate company
INSERT INTO Firma (nazwa,NIP,branza) VALUES 
("PolBud S.A.","123123123123","budowlana"),
("Polskie Szynki sp.z.o.o.","586985002145","N/A"),
("Klikanie w komputer S.A.","200245599966","IT")
