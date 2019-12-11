package app.service.implementations;

import app.dao.interfaces.FactoryDAO;
import app.entities.News;
import app.service.interfaces.NewsService;
import app.utils.MyConstants;

import java.sql.Date;

public class NewsServiceImpl implements NewsService {

    private FactoryDAO factoryDAO;

    public NewsServiceImpl() {
        factoryDAO = FactoryDAO.getInstance();
    }

    @Override
    public Boolean createNewsInDB(String title, String author, String postDate, String textPath) {
        News news = new News();
        news.setId(1);
        news.setTitle(title);
        news.setAuthor(author);
        news.setPostDate(postDate);
        news.setTextPath(textPath);
        return factoryDAO.createNewsDAO().create(news);
    }
}
