package Repository;

public class WriteAheadLogEntry<T> {
    private OperationType operationType;
    private T entity;
    private String id;
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WriteAheadLogEntry(OperationType operationType, String className, T entity, String id) {
        this.operationType = operationType;
        this.className = className;
        this.entity = entity;
        this.id = id;
    }

    public WriteAheadLogEntry(OperationType operationType, String className, T entity) {
        this.operationType = operationType;
        this.className = className;
        this.entity = entity;
    }

    public WriteAheadLogEntry(OperationType operationType, String className, String id) {
        this.operationType = operationType;
        this.className = className;
        this.id = id;
    }
}
