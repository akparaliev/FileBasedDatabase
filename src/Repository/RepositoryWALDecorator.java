package Repository;

public class RepositoryWALDecorator<T> extends RepositoryDecorator<T> {
    private WriteAheadLog<T> writeAheadLog;
    public RepositoryWALDecorator(IRepository<T> repo, String walFileName) {
        super(repo);
        writeAheadLog = new WriteAheadLog<T>(walFileName);
    }

    @Override
    public String Add(T entity) {
        String id = super.Add(entity);
        writeAheadLog.LogAdd(id, entity.getClass().getSimpleName(), entity);
        return id;
    }

    @Override
    public void Update(T entity) {
        writeAheadLog.LogUpdate(entity.getClass().getSimpleName(), entity);
        super.Update(entity);
    }

    @Override
    public void Remove(String id) {
        writeAheadLog.LogRemove(this.getClass().getSimpleName(), id);
        super.Remove(id);
    }
}
