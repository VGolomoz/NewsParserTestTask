package app.utils;

public class MyConstants {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/newsParser_db?serverTimezone=UTC";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "asDF1506";

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/71.0";
    public static final String CSS_QUERY_WITH_LAST_PUBLICATIONS = "section[class=\"publications-archive\"]";
    public static final String CSS_QUERY_WITH_NEWS_POST_DATE = "div[class=\"item time no-padding\"]";
    public static final String CSS_QUERY_WITH_NEWS_TEXT = "p";

    public static final String POLITICS_CATEGORY = "https://www.unian.net/politics/";
    public static final String WAR_CATEGORY = "https://www.unian.net/war/";
    public static final String SOCIETY_CATEGORY = "https://www.unian.net/society/";
    public static final String WORLD_CATEGORY = "https://www.unian.net/world/";
    public static final String HEALTH_CATEGORY = "https://www.unian.net/health/";
}
