package app.service.implementations;

import app.dao.interfaces.FactoryDAO;
import app.entities.News;
import app.service.interfaces.NewsService;

public class NewsServiceImpl implements NewsService {

    private FactoryDAO factoryDAO;

    public NewsServiceImpl() {
        factoryDAO = FactoryDAO.getInstance();
    }

    @Override
    public Boolean createNewsInDB(String title, String postDate, String textPath) {
        News news = new News();
        news.setTitle(title);
        news.setPostDate(postDate);
        news.setTextPath(textPath);
        return factoryDAO.createNewsDAO().create(news);
    }
}
