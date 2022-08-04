-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2021-06-12 12:26:33.649

-- tables
-- Table: Administracja
CREATE TABLE Administracja (
    Pracownik_id int NOT NULL,
    zakres varchar(5000) NOT NULL,
    CONSTRAINT Administracja_pk PRIMARY KEY (Pracownik_id)
);

-- Table: Firma
CREATE TABLE Firma (
    id int NOT NULL AUTO_INCREMENT,
    nazwa varchar(255) NOT NULL,
    NIP varchar(12) NOT NULL,
    branza varchar(255) NOT NULL,
    CONSTRAINT Firma_pk PRIMARY KEY (id)
);

-- Table: Klient
CREATE TABLE Klient (
    id int NOT NULL AUTO_INCREMENT,
    imie varchar(100) NOT NULL,
    nazwisko varchar(100) NOT NULL,
    data_ur date NULL,
    numer varchar(30) NULL,
    data_rejestracji date NOT NULL,
    Firma_id int NULL,
    CONSTRAINT Klient_pk PRIMARY KEY (id)
);

-- Table: Kort
CREATE TABLE Kort (
    id int NOT NULL AUTO_INCREMENT,
    nawierzchnia varchar(255) NOT NULL,
    cena int NOT NULL,
    oswietlenie bool NOT NULL,
    kryty bool NOT NULL,
    CONSTRAINT Kort_pk PRIMARY KEY (id)
);

-- Table: Kort_Prace
CREATE TABLE Kort_Prace (
    Kort_id int NOT NULL,
    Prace_techniczne_id int NOT NULL,
    CONSTRAINT Kort_Prace_pk PRIMARY KEY (Kort_id,Prace_techniczne_id)
);

-- Table: Ksiegowy
CREATE TABLE Ksiegowy (
    Pracownik_id int NOT NULL,
    klienci varchar(1000) NOT NULL,
    dostep bool NOT NULL,
    CONSTRAINT Ksiegowy_pk PRIMARY KEY (Pracownik_id)
);

-- Table: Podwykonawca
CREATE TABLE Podwykonawca (
    id int NOT NULL AUTO_INCREMENT,
    NIP int NOT NULL,
    zakres varchar(1000) NOT NULL,
    numer int NOT NULL,
    cena_rb int NOT NULL,
    CONSTRAINT Podwykonawca_pk PRIMARY KEY (id)
);

-- Table: Prace_techniczne
CREATE TABLE Prace_techniczne (
    id int NOT NULL AUTO_INCREMENT,
    od date NOT NULL,
    do date NOT NULL,
    opis varchar(5000) NULL,
    Podwykonawca_id int NOT NULL,
    CONSTRAINT Prace_techniczne_pk PRIMARY KEY (id)
);

-- Table: Pracownik
CREATE TABLE Pracownik (
    id int NOT NULL AUTO_INCREMENT,
    imie varchar(100) NOT NULL,
    nazwisko varchar(100) NOT NULL,
    data_ur date NULL,
    numer varchar(30) NULL,
    data_zatrudnienia date NOT NULL,
    pensja double(6,2) NOT NULL,
    CONSTRAINT Pracownik_pk PRIMARY KEY (id)
);

-- Table: Pracownik_Urlop
CREATE TABLE Pracownik_Urlop (
    Pracownik_id int NOT NULL,
    Urlop_id int NOT NULL,
    CONSTRAINT Pracownik_Urlop_pk PRIMARY KEY (Pracownik_id,Urlop_id)
);

-- Table: Recepcjonista
CREATE TABLE Recepcjonista (
    Pracownik_id int NOT NULL,
    pensja double(6,2) NOT NULL,
    CONSTRAINT Recepcjonista_pk PRIMARY KEY (Pracownik_id)
);

-- Table: Rezerwacja
CREATE TABLE Rezerwacja (
    id int NOT NULL AUTO_INCREMENT,
    Kort_id int NOT NULL,
    Klient_id int NOT NULL,
    Trener_id int NULL,
    data date NOT NULL,
    od time NOT NULL,
    do time NOT NULL,
    status varchar(100) NOT NULL,
    CONSTRAINT Rezerwacja_pk PRIMARY KEY (id)
);

-- Table: Trener
CREATE TABLE Trener (
    id int NOT NULL AUTO_INCREMENT,
    imie varchar(100) NOT NULL,
    nazwisko varchar(100) NOT NULL,
    data_ur date NULL,
    numer varchar(30) NULL,
    poziom varchar(1000) NOT NULL,
    opis varchar(5000) NOT NULL,
    CONSTRAINT Trener_pk PRIMARY KEY (id)
);

-- Table: Urlop
CREATE TABLE Urlop (
    id int NOT NULL AUTO_INCREMENT,
    typ varchar(255) NOT NULL,
    od date NOT NULL,
    do date NOT NULL,
    CONSTRAINT Urlop_pk PRIMARY KEY (id)
);

-- Table: Wydarzenie
CREATE TABLE Wydarzenie (
    id int NOT NULL AUTO_INCREMENT,
    Firma_id int NOT NULL,
    nazwa varchar(255) NOT NULL,
    ilosc int NOT NULL,
    bufet bool NOT NULL,
    CONSTRAINT Wydarzenie_pk PRIMARY KEY (id)
);

-- Table: Wydarzenie_Rezerwacja
CREATE TABLE Wydarzenie_Rezerwacja (
    Rezerwacja_id int NOT NULL,
    Wydarzenie_id int NOT NULL,
    CONSTRAINT Wydarzenie_Rezerwacja_pk PRIMARY KEY (Rezerwacja_id,Wydarzenie_id)
);

-- Table: user
CREATE TABLE user (
    login varchar(255) NOT NULL,
    pass varchar(255) NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (login)
);

-- foreign keys
-- Reference: Administracja_Pracownik (table: Administracja)
ALTER TABLE Administracja ADD CONSTRAINT Administracja_Pracownik FOREIGN KEY Administracja_Pracownik (Pracownik_id)
    REFERENCES Pracownik (id);

-- Reference: Klient_Firma (table: Klient)
ALTER TABLE Klient ADD CONSTRAINT Klient_Firma FOREIGN KEY Klient_Firma (Firma_id)
    REFERENCES Firma (id);

-- Reference: Kort_Prace_Kort (table: Kort_Prace)
ALTER TABLE Kort_Prace ADD CONSTRAINT Kort_Prace_Kort FOREIGN KEY Kort_Prace_Kort (Kort_id)
    REFERENCES Kort (id);

-- Reference: Kort_Prace_Prace_techniczne (table: Kort_Prace)
ALTER TABLE Kort_Prace ADD CONSTRAINT Kort_Prace_Prace_techniczne FOREIGN KEY Kort_Prace_Prace_techniczne (Prace_techniczne_id)
    REFERENCES Prace_techniczne (id);

-- Reference: Ksiegowy_Pracownik (table: Ksiegowy)
ALTER TABLE Ksiegowy ADD CONSTRAINT Ksiegowy_Pracownik FOREIGN KEY Ksiegowy_Pracownik (Pracownik_id)
    REFERENCES Pracownik (id);

-- Reference: Prace_techniczne_Podwykonawca (table: Prace_techniczne)
ALTER TABLE Prace_techniczne ADD CONSTRAINT Prace_techniczne_Podwykonawca FOREIGN KEY Prace_techniczne_Podwykonawca (Podwykonawca_id)
    REFERENCES Podwykonawca (id);

-- Reference: Pracownik_Urlop_Pracownik (table: Pracownik_Urlop)
ALTER TABLE Pracownik_Urlop ADD CONSTRAINT Pracownik_Urlop_Pracownik FOREIGN KEY Pracownik_Urlop_Pracownik (Pracownik_id)
    REFERENCES Pracownik (id);

-- Reference: Pracownik_Urlop_Urlop (table: Pracownik_Urlop)
ALTER TABLE Pracownik_Urlop ADD CONSTRAINT Pracownik_Urlop_Urlop FOREIGN KEY Pracownik_Urlop_Urlop (Urlop_id)
    REFERENCES Urlop (id);

-- Reference: Recepcjonista_Pracownik (table: Recepcjonista)
ALTER TABLE Recepcjonista ADD CONSTRAINT Recepcjonista_Pracownik FOREIGN KEY Recepcjonista_Pracownik (Pracownik_id)
    REFERENCES Pracownik (id);

-- Reference: Rezerwacja_Klient (table: Rezerwacja)
ALTER TABLE Rezerwacja ADD CONSTRAINT Rezerwacja_Klient FOREIGN KEY Rezerwacja_Klient (Klient_id)
    REFERENCES Klient (id);

-- Reference: Rezerwacja_Kort (table: Rezerwacja)
ALTER TABLE Rezerwacja ADD CONSTRAINT Rezerwacja_Kort FOREIGN KEY Rezerwacja_Kort (Kort_id)
    REFERENCES Kort (id);

-- Reference: Rezerwacja_Trener (table: Rezerwacja)
ALTER TABLE Rezerwacja ADD CONSTRAINT Rezerwacja_Trener FOREIGN KEY Rezerwacja_Trener (Trener_id)
    REFERENCES Trener (id);

-- Reference: Wydarzenie_Firma (table: Wydarzenie)
ALTER TABLE Wydarzenie ADD CONSTRAINT Wydarzenie_Firma FOREIGN KEY Wydarzenie_Firma (Firma_id)
    REFERENCES Firma (id);

-- Reference: Wydarzenie_Rezerwacja_Rezerwacja (table: Wydarzenie_Rezerwacja)
ALTER TABLE Wydarzenie_Rezerwacja ADD CONSTRAINT Wydarzenie_Rezerwacja_Rezerwacja FOREIGN KEY Wydarzenie_Rezerwacja_Rezerwacja (Rezerwacja_id)
    REFERENCES Rezerwacja (id);

-- Reference: Wydarzenie_Rezerwacja_Wydarzenie (table: Wydarzenie_Rezerwacja)
ALTER TABLE Wydarzenie_Rezerwacja ADD CONSTRAINT Wydarzenie_Rezerwacja_Wydarzenie FOREIGN KEY Wydarzenie_Rezerwacja_Wydarzenie (Wydarzenie_id)
    REFERENCES Wydarzenie (id);

-- End of file.

