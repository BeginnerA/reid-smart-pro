package top.reid.smart.db.config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DriverClassTest {

    public static String driverClass = "com.mysql.cj.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/reid-smart";

    @Test
    void loadDriverClass() {
        //DriverClass.loadDriverClass(driverClass, null);
        DriverClass.loadDriverClass(null, url);
    }

    @Test
    void getConnection() {
        //Connection connection = DriverClass.getConnection(driverClass, url, "root", "root");
        //Connection connection = DriverClass.getConnection(null, url, "root", "root");
        Connection connection = DriverClass.getConnection("net.ucanaccess.jdbc.UcanaccessDriver", "jdbc:ucanaccess://E:\\test.mdb", "", "");
        System.out.println(connection);
    }

    @Test
    void getDriverClassByUrl() {
        String driverClassByUrl = DriverClass.getDriverClassByUrl(url);
        System.out.println(driverClassByUrl);
    }

    @Test
    void closeConnection() throws SQLException {
        Connection connection = DriverClass.getConnection(driverClass, url, "root", "root");
        System.out.println("状态：" + connection.isClosed());
        DriverClass.closeConnection(connection);
        System.out.println("状态：" + connection.isClosed());
    }
}
