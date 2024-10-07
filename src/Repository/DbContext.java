package Repository;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DbContext {
    private final String FILENAME;
    
    public DbContext(String filename){
        FILENAME = filename;
        createFileIfNew();
    }

    public DbSet GetDatabase() {
        DbSet database = new DbSet();
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILENAME))){
            database = (DbSet) objectInputStream.readObject(); 
        } catch (EOFException e) {
           
        } 
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return database;
    }

    public void SaveChanges(DbSet database) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            objectOutputStream.writeObject(database); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileIfNew(){
        File file = new File(FILENAME);
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                } else {
                    System.out.println("Failed to create file: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }
}
