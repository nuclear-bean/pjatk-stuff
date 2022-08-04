package app.database;

public enum Queries {
    LOGIN("SELECT pass FROM user WHERE login = ?"),
    GET_CLIENTS("SELECT * FROM Klient"),
    GET_TRAINERS("SELECT * FROM Trener"),
    GET_COURTS("SELECT * FROM Kort"),
    GET_COMPANIES("SELECT * FROM Firma"),
    GET_RESERVATIONS("SELECT * FROM Rezerwacja"),
    ADD_COMPANY("INSERT INTO Firma (nazwa,NIP,branza) VALUES (?,?,?)"),
    UPDATE_CLIENT("UPDATE Klient SET numer=?,Firma_id=? WHERE id=?"),
    CREATE_CLIENT("INSERT INTO Klient (imie,nazwisko,data_ur,numer,data_rejestracji) VALUES (?,?,?,?,?)"),
    CREATE_RESERVATION("INSERT INTO Rezerwacja (Kort_id,Klient_id,Trener_id,data,od,do,status) VALUES (?,?,?,?,?,?,?)"),
    GET_LAST_RESERVATION_ID("SELECT MAX(id) FROM Rezerwacja");

    final String expression;
    Queries(String expression) {
        this.expression = expression;
    }
}
