package app.dao.interfaces;

import java.util.List;

public interface DAO<T> extends AutoCloseable {
    void create(T entity);

    T getById(Long id);

    boolean delete(Long id);

    @Override
    void close();
}
