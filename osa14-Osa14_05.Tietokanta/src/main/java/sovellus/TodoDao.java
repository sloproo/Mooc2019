package sovellus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoDao {

    private String tietokannanPolku;

    public TodoDao(String tietokannanPolku) {
        this.tietokannanPolku = tietokannanPolku;
    }

    public List<Todo> listaa() throws SQLException {
        List<Todo> todot = new ArrayList<>();
        try (Connection yhteys = luoYhteysJaVarmistaTietokanta();
                ResultSet tulokset = yhteys.prepareStatement("SELECT * FROM Todo").executeQuery()) {
            while (tulokset.next()) {
                todot.add(new Todo(tulokset.getInt("id"), tulokset.getString("nimi"), tulokset.getString("kuvaus"), tulokset.getBoolean("valmis")));
            }
        }
        return todot;
    }

    public void lisaa(Todo todo) throws SQLException {
        try (Connection yhteys = luoYhteysJaVarmistaTietokanta()) {
            PreparedStatement stmt = yhteys.prepareStatement("INSERT INTO Todo (nimi, kuvaus, valmis) VALUES (?, ?, ?)");
            stmt.setString(1, todo.getNimi());
            stmt.setString(2, todo.getKuvaus());
            stmt.setBoolean(3, todo.getValmis());
            stmt.executeUpdate();
        }
    }

    public void asetaTehdyksi(int id) throws SQLException {
        try (Connection yhteys = luoYhteysJaVarmistaTietokanta()) {
            PreparedStatement stmt = yhteys.prepareStatement("UPDATE Todo SET valmis = true WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void poista(int id) throws SQLException {
        try (Connection yhteys = luoYhteysJaVarmistaTietokanta()) {
            PreparedStatement stmt = yhteys.prepareStatement("DELETE FROM Todo WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Connection luoYhteysJaVarmistaTietokanta() throws SQLException {
        Connection conn = DriverManager.getConnection(this.tietokannanPolku, "sa", "");
        try {
            conn.prepareStatement("CREATE TABLE Todo (id int auto_increment primary key, nimi varchar(255), kuvaus varchar(10000), valmis boolean)").execute();
        } catch (SQLException t) {
        }

        return conn;
    }
}
