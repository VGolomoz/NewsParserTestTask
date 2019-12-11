package app.dao.queries;

public enum NewsQuery {

    INSERT_INTO_NEWS_TABLE("INSERT INTO " + NewsTable.NEWS + " (" + NewsTable.TITLE + ", " + NewsTable.AUTHOR + ", " +
            "" + NewsTable.POST_DATE + ", " + NewsTable.TEXT_PATH + ") VALUES ((?),(?),(?),(?))"),

    GET_NEWS_BY_ID("SELECT * FROM `" + NewsTable.NEWS + "` " + "WHERE `" + NewsTable.ID + "` =(?)"),

    DELETE_NEWS_BY_ID("DELETE FROM `" + NewsTable.NEWS + "` " + "WHERE `" + NewsTable.ID + "` =(?)");

    NewsQuery(String query) {
    }
}
