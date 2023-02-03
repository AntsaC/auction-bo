package services;

import java.sql.*;

public class Connexion {
    public static Connection con = null;

    public Connexion() {
    }

    public static Connection getConnection() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://containers-us-west-181.railway.app:6466/railway", "postgres",
                    "qfbCq1z7zXud824YeZOT");
        } catch (Exception e) {
            throw e;
        }
    }
}