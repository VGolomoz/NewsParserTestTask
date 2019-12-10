package app.config;

import app.utils.MyConstants;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setUrl(MyConstants.DB_URL);
        dataSource.setUsername(MyConstants.DB_USER);
        dataSource.setPassword(MyConstants.DB_PASSWORD);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(20);
        dataSource.setInitialSize(5);
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(("SQLException occurred in DBCPDataSource.class from getConnection() method" + e));
        }
        return connection;
    }

    private DBCPDataSource() {
    }
}
