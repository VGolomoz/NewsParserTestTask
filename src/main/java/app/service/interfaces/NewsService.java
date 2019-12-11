package app.service.interfaces;

import java.sql.Date;

public interface NewsService {

    Boolean createNewsInDB(String title, String author, String postDate, String textPath);
}
