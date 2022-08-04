package app.database;

import app.credentials.Hasher;
import app.data.Company;
import app.data.Court;
import app.data.events.Reservation;
import app.data.events.ReservationStatus;
import app.data.person.Client;
import app.data.person.Trainer;
import java.sql.*;

public class DatabaseConnector {
    private static final String DB_ADDRESS = "185.201.115.126";
    private static final String DB_USERNAME = "mas_user";
    private static final String DB_PASSWORD = "Mas123!!!";
    private static final String DB_NAME = "mas";
    private static final int DB_PORT = 3306;

    public static String current_user = "";

    @SuppressWarnings("unused")
    public static void testConnection(){
        try {
            Connection connection = connect();
            close(connection);
            System.out.println("connection successful");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection connect() throws SQLException {
        Connection connection;

        //establish connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e){
            System.out.println("A fatal error occurred:\n" + e.getMessage());
        }
        String address = "jdbc:mysql://" + DB_ADDRESS + ':' + DB_PORT + '/' + DB_NAME + "?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false";
        connection = DriverManager.getConnection(address, DB_USERNAME, DB_PASSWORD);

        return connection;
    }

    public static void close(Connection connection){
        try{
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void getClients() throws SQLException {
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(Queries.GET_CLIENTS.expression);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String imie = resultSet.getString(2);
            String nazwisko = resultSet.getString(3);
            Date data_ur = resultSet.getDate(4);
            int numer = resultSet.getInt(5);
            Date data_rej = resultSet.getDate(6);
            int firma_id = resultSet.getInt(7);

            Client client = new Client(id,imie,nazwisko,numer,data_ur,data_rej);
            client.firma_id = firma_id;
        }

        close(connection);
    }

    public static void getTrainers() throws SQLException {
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(Queries.GET_TRAINERS.expression);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String imie = resultSet.getString(2);
            String nazwisko = resultSet.getString(3);
            Date data_ur = resultSet.getDate(4);
            int numer = resultSet.getInt(5);
            String poziom = resultSet.getString(6);
            String opis = resultSet.getString(7);

            new Trainer(id,imie,nazwisko,numer,data_ur,poziom,opis);
        }
        close(connection);
    }

    public static void getCourts() throws SQLException {
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(Queries.GET_COURTS.expression);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String nawierzchnia = resultSet.getString(2);
            int cena = resultSet.getInt(3);
            boolean oswietlenie = resultSet.getBoolean(4);
            boolean kryty = resultSet.getBoolean(4);
            new Court(id,cena,nawierzchnia,oswietlenie,kryty);
        }
        close(connection);
    }

    public static void getCompanies() throws SQLException {
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(Queries.GET_COMPANIES.expression);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String nazwa = resultSet.getString(2);
            String nip = resultSet.getString(3);
            String branza = resultSet.getString(4);
            new Company(id,nazwa,nip,branza);
        }
    }

    public static void getReservations() throws SQLException{
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(Queries.GET_RESERVATIONS.expression);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            int klient_id = resultSet.getInt(2);
            int kort_id = resultSet.getInt(3);
            int trener_id = resultSet.getInt(4);
            Date data = resultSet.getDate(5);
            Time czas_od = resultSet.getTime(6);
            Time czas_do = resultSet.getTime(7);
            String status = resultSet.getString(8);

            new Reservation(id,klient_id,kort_id,trener_id,data,czas_od,czas_do, ReservationStatus.valueOf(status));
        }
        close(connection);
    }

    public static boolean authorize(String user, String pass) throws SQLException {
        current_user = user;

        String db_pass;
        String hashed_pass = Hasher.hash(pass);

        // connect to database and get correct pass hash for this user
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(Queries.LOGIN.expression);
        statement.setString(1,user);
        ResultSet resultSet = statement.executeQuery();

        try {
            resultSet.next();
            db_pass = resultSet.getString(1);
        } catch (SQLException e){
            if(e.getMessage().contains("empty result set"))
                return false;
            else
                throw e;
        }
        close(connection);

        //verify if password correct
        return db_pass.equals(hashed_pass);
    }

    public static void addCompany(Company company) throws SQLException {
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(Queries.ADD_COMPANY.expression);
        statement.setString(1,company.nazwa);
        statement.setString(2,company.NIP);
        statement.setString(3,company.branza);
        statement.executeUpdate();
        close(connection);
    }

    public static void updateClient(Client client) throws SQLException {
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_CLIENT.expression);
        statement.setInt(1,client.getNumer());
        statement.setInt(2,client.firma_id);
        statement.setInt(3,client.id);
        statement.executeUpdate();
        close(connection);
    }

    public static void createClient(Client client) throws SQLException {
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(Queries.CREATE_CLIENT.expression);
        statement.setString(1,client.imie);
        statement.setString(2,client.nazwisko);
        statement.setDate(3,client.data_ur);
        statement.setInt(4,client.getNumer());
        statement.setDate(5,client.data_rejestracji);
        statement.executeUpdate();
        close(connection);
    }

    public static void createReservation(Reservation reservation) throws SQLException{
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(Queries.CREATE_RESERVATION.expression);
        statement.setInt(1,reservation.kortId);
        statement.setInt(2,reservation.klientId);
        if(reservation.trenerId !=0)
            statement.setInt(3,reservation.trenerId);
        else
            statement.setNull(3, Types.INTEGER);
        statement.setDate(4,reservation.data);
        statement.setTime(5,reservation.czas_od);
        statement.setTime(6,reservation.czas_do);
        statement.setString(7,reservation.status.name());
        System.out.println(statement.toString());
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement(Queries.GET_LAST_RESERVATION_ID.expression);
        ResultSet resultSet = statement1.executeQuery();
        resultSet.next();
        reservation.id = resultSet.getInt(1);
        close(connection);
    }
}