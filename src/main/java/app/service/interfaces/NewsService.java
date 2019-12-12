package app.service.interfaces;

public interface NewsService {

    Boolean createNewsInDB(String title, String postDate, String textPath);
}
