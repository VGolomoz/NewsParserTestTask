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

public class NewsJDBC implements NewsDAO {

    private Connection connection;

    public NewsJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(News news) {
        try (PreparedStatement ps = connection.prepareStatement(NewsQuery.INSERT_INTO_NEWS_TABLE.name())) {
            ps.setLong(1, news.getId());
            ps.setString(2, news.getTitle());
            ps.setString(3, news.getAuthor());
            ps.setDate(4, news.getPostDate());
            ps.setString(5, news.getTextPath());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public News getById(Long id) {
        Mapper<News> newsMapper = new NewsMapper();
        News newsFromDB = null;

        try (PreparedStatement ps = connection.prepareStatement(NewsQuery.GET_NEWS_BY_ID.name())) {
            ps.setLong(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                newsFromDB = newsMapper.getEntity(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newsFromDB;
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(NewsQuery.DELETE_NEWS_BY_ID.name())) {
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
