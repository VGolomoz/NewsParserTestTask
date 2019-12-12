package app.entities;

import java.util.Objects;

public class News {

    private Integer id;
    private String title;
    private String postDate;
    private String textPath;

    public News() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getTextPath() {
        return textPath;
    }

    public void setTextPath(String textPath) {
        this.textPath = textPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id) &&
                Objects.equals(title, news.title) &&
                Objects.equals(postDate, news.postDate) &&
                Objects.equals(textPath, news.textPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, postDate, textPath);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", postDate='" + postDate + '\'' +
                ", textPath='" + textPath + '\'' +
                '}';
    }
}
