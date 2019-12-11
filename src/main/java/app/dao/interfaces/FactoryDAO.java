package app.dao.interfaces;

import app.dao.implementations.FactoryJDBC;

public abstract class FactoryDAO {

    private static FactoryDAO factoryDAO;

    public static FactoryDAO getInstance() {
        if (factoryDAO == null)
            synchronized (FactoryDAO.class) {
                    factoryDAO = new FactoryJDBC();
            }
        return factoryDAO;
    }

    public abstract NewsDAO createNewsDAO();

}
