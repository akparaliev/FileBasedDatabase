package Repository;

import java.util.Map;

public interface IRepository<T> {
    Map<String,T> GetAll();
    T GetById(String id);
    String Add(T entity);
    void Update(T entity);
    void Remove(String id);
}
