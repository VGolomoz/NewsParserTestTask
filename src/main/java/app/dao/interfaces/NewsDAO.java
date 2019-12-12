package app.dao.interfaces;

import app.entities.News;

import java.util.List;

public interface NewsDAO extends DAO<News> {

    List<News> getAllNews();
}
