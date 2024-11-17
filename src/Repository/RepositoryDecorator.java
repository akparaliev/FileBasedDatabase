package Repository;

import java.util.Map;

public class RepositoryDecorator<T> implements IRepository<T> {
    private final IRepository<T> repo;
    public RepositoryDecorator(IRepository<T> repo) {
        this.repo = repo;
    }

    @Override
    public Map<String, T> GetAll() {
        return repo.GetAll();
    }

    @Override
    public T GetById(String id) {
        return repo.GetById(id);
    }

    @Override
    public String Add(T entity) {
        return repo.Add(entity);
    }

    @Override
    public void Update(T entity) {
        repo.Update(entity);
    }

    @Override
    public void Remove(String id) {
        repo.Remove(id);
    }
    
}
