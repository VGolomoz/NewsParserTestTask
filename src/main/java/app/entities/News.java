package app.entities;

import java.sql.Date;
import java.util.Objects;

public class News {

    private Long id;
    private String title;
    private String author;
    private Date postDate;
    private String textPath;

    public News() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
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
                Objects.equals(author, news.author) &&
                Objects.equals(postDate, news.postDate) &&
                Objects.equals(textPath, news.textPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, postDate, textPath);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", postDate=" + postDate +
                ", text='" + textPath + '\'' +
                '}';
    }
}
