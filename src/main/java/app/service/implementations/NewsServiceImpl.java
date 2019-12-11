package app.service.implementations;

import app.dao.interfaces.FactoryDAO;
import app.service.interfaces.NewsService;

public class NewsServiceImpl implements NewsService {

    private FactoryDAO factoryDAO;

    public NewsServiceImpl() {
        factoryDAO = FactoryDAO.getInstance();
    }

    @Override
    public Boolean createUserInDB() {
        factoryDAO.createNewsDAO().create(News);
    }
}
