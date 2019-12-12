package app.dao.interfaces;

public interface DAO<T> extends AutoCloseable {
    Boolean create(T entity);

    Boolean delete(Long id);

    @Override
    void close();
}
