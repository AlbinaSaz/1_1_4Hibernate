package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String url = "jdbc:MySQL://localhost:3306/users";
    private static String username ="root";
    private static String password = "fkm,byfA1212";

    // реализуйте настройку соеденения с БД
    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
    public static void driver() throws ClassNotFoundException {
        Class.forName("mysql-connector-java-8.0.29");
    }
}
