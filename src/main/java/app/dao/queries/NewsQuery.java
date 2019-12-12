package app.dao.queries;

import static app.dao.queries.NewsTable.*;

public enum NewsQuery {

    INSERT_INTO_NEWS_TABLE("INSERT INTO " + NEWS + " (" + TITLE + ", " +
            "" + POST_DATE + ", " + TEXT_PATH + ") VALUES ((?),(?),(?))"),

    GET_ALL_NEWS("SELECT * FROM `" + NEWS),

    DELETE_NEWS_BY_ID("DELETE FROM `" + NEWS + "` " + "WHERE `" + ID + "` =(?)");

    String query;

    NewsQuery(String query) {
        this.query = query;
    }

    public String getQuery(){
        return query;
    }
}
