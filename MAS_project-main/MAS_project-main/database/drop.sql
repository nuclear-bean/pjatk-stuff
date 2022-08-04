-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2021-06-12 11:46:49.606

-- foreign keys
ALTER TABLE Administracja
    DROP FOREIGN KEY Administracja_Pracownik;

ALTER TABLE Klient
    DROP FOREIGN KEY Klient_Firma;

ALTER TABLE Kort_Prace
    DROP FOREIGN KEY Kort_Prace_Kort;

ALTER TABLE Kort_Prace
    DROP FOREIGN KEY Kort_Prace_Prace_techniczne;

ALTER TABLE Ksiegowy
    DROP FOREIGN KEY Ksiegowy_Pracownik;

ALTER TABLE Prace_techniczne
    DROP FOREIGN KEY Prace_techniczne_Podwykonawca;

ALTER TABLE Pracownik_Urlop
    DROP FOREIGN KEY Pracownik_Urlop_Pracownik;

ALTER TABLE Pracownik_Urlop
    DROP FOREIGN KEY Pracownik_Urlop_Urlop;

ALTER TABLE Recepcjonista
    DROP FOREIGN KEY Recepcjonista_Pracownik;

ALTER TABLE Rezerwacja
    DROP FOREIGN KEY Rezerwacja_Klient;

ALTER TABLE Rezerwacja
    DROP FOREIGN KEY Rezerwacja_Kort;

ALTER TABLE Rezerwacja
    DROP FOREIGN KEY Rezerwacja_Trener;

ALTER TABLE Wydarzenie
    DROP FOREIGN KEY Wydarzenie_Firma;

ALTER TABLE Wydarzenie_Rezerwacja
    DROP FOREIGN KEY Wydarzenie_Rezerwacja_Rezerwacja;

ALTER TABLE Wydarzenie_Rezerwacja
    DROP FOREIGN KEY Wydarzenie_Rezerwacja_Wydarzenie;

-- tables
DROP TABLE Administracja;

DROP TABLE Firma;

DROP TABLE Klient;

DROP TABLE Kort;

DROP TABLE Kort_Prace;

DROP TABLE Ksiegowy;

DROP TABLE Podwykonawca;

DROP TABLE Prace_techniczne;

DROP TABLE Pracownik;

DROP TABLE Pracownik_Urlop;

DROP TABLE Recepcjonista;

DROP TABLE Rezerwacja;

DROP TABLE Trener;

DROP TABLE Urlop;

DROP TABLE Wydarzenie;

DROP TABLE Wydarzenie_Rezerwacja;

DROP TABLE user;

-- End of file.

