package utils;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtils {

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
