package felipe.nascimento.dao;

import java.util.List;

public interface DaoInterface<T> {
    Boolean insert(T entity);
    Boolean update(T entity);
    Boolean delete(Long pk);
    T select(Long pk);
    List<T> selectAll();
}