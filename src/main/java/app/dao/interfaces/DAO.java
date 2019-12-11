package app.dao.interfaces;

public interface DAO<T> extends AutoCloseable {
    Boolean create(T entity);

    T getById(Long id);

    Boolean delete(Long id);

    @Override
    void close();
}
