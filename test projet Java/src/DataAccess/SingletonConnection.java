package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    private static Connection uniqueConnection;

    public static Connection getInstance()throws SQLException{
        if(uniqueConnection == null){
                uniqueConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/java_projet?serverTimezone=Europe/Paris","root","password");
        }
        return uniqueConnection;
    }
}
