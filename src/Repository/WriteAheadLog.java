package Repository;

import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;

public class WriteAheadLog<T> {
    private final String FILENAME;

    public WriteAheadLog(String fileName) {
        FILENAME = fileName;
        FileUtil.createFileIfNew(FILENAME);
    }

    public void LogAdd(String id, String className, T entity) {
        WriteAheadLogEntry<T> entry = new WriteAheadLogEntry<>(OperationType.ADD, className, entity, id);
        Gson gson = new Gson();
        String entryJson = gson.toJson(entry);
        SaveToFile(entryJson);
    }

    public void LogUpdate(String className, T entity) {
        WriteAheadLogEntry<T> entry = new WriteAheadLogEntry<>(OperationType.UPDATE, className, entity);
        Gson gson = new Gson();
        String entryJson = gson.toJson(entry);
        SaveToFile(entryJson);
    }

    public void LogRemove(String className, String id) {
        WriteAheadLogEntry<T> entry = new WriteAheadLogEntry<>(OperationType.REMOVE, className, id);
        Gson gson = new Gson();
        String entryJson = gson.toJson(entry);
        SaveToFile(entryJson);
    }

    private void SaveToFile(String entryJson){
        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            writer.write(entryJson);
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
