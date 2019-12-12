package app.dao.implementations;

import app.dao.interfaces.NewsDAO;
import app.dao.mappers.Mapper;
import app.dao.mappers.NewsMapper;
import app.dao.queries.NewsQuery;
import app.entities.News;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NewsJDBC implements NewsDAO {

    private Connection connection;

    NewsJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Boolean create(News news) {
        try (PreparedStatement ps = connection.prepareStatement(NewsQuery.INSERT_INTO_NEWS_TABLE.getQuery())) {
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getPostDate());
            ps.setString(3, news.getTextPath());
            int updateSuccessful = 1;
            return ps.executeUpdate() == updateSuccessful;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<News> getAllNews() {
        Mapper<News> newsMapper = new NewsMapper();
        List<News> news = null;

        try (PreparedStatement ps = connection.prepareStatement(NewsQuery.GET_ALL_NEWS.getQuery())) {
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                News newsFromDB = newsMapper.getEntity(rs);
                news.add(newsFromDB);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return news;
    }

    @Override
    public Boolean delete(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(NewsQuery.DELETE_NEWS_BY_ID.getQuery())) {
            ps.setLong(1, id);
            int updateSuccessful = 1;
            return ps.executeUpdate() == updateSuccessful;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
