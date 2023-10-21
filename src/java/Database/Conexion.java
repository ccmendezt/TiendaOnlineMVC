package Database;

import java.sql.*;

public class Conexion {

    private Connection con;
    private String url = "jdbc:mysql://localhost:3306/bdcarritocompras";
    private String user = "root";
    private String pass = "";

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá el mensaje de error en la consola
            // También puedes lanzar una excepción personalizada aquí para manejarla más adecuadamente
        }
        return con;
    }
}
