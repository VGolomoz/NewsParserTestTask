package app.dao.implementations;

import app.config.DBCPDataSource;
import app.dao.interfaces.FactoryDAO;
import app.dao.interfaces.NewsDAO;

import java.sql.Connection;

public class FactoryJDBC extends FactoryDAO {

    private Connection connection = DBCPDataSource.getConnection();

    @Override
    public NewsDAO createNewsDAO() {
        return new NewsJDBC(connection);
    }
}
