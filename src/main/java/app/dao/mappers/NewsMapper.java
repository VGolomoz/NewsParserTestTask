package app.dao.mappers;

import app.dao.queries.NewsTable;
import app.entities.News;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsMapper implements Mapper<News> {

    @Override
    public News getEntity(ResultSet rs) throws SQLException {

        News news = new News();
        news.setId(rs.getInt(NewsTable.ID));
        news.setTitle(rs.getString(NewsTable.TITLE));
        news.setAuthor(rs.getString(NewsTable.AUTHOR));
        news.setPostDate(rs.getString(NewsTable.POST_DATE));
        news.setTextPath(rs.getString(NewsTable.TEXT_PATH));

        return news;
    }
}
