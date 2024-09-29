package Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import Entities.AcademyGroup;

public class AcademyGroupRepository implements IRepository<AcademyGroup> {
    private List<AcademyGroup> academyGroups;
    private final String FILENAME;
    
    public AcademyGroupRepository(String filename){
        academyGroups = new ArrayList<AcademyGroup>();
        FILENAME = filename;
        createFileIfNew();
    }

    @Override
    public List<AcademyGroup> GetAll() {
       return academyGroups;
    }

    @Override
    public AcademyGroup GetById(int id) {
        return academyGroups.stream()
            .filter(group -> group.getId() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public void Add(AcademyGroup academyGroup) {
        academyGroups.add(academyGroup);
    }

    @Override
    public void Update(AcademyGroup entity) {
        int index = academyGroups.indexOf(entity);
        if (index != -1) {
            academyGroups.set(index, entity);
        }
    }

    @Override
    public void Remove(AcademyGroup academyGroup) {
        academyGroups.remove(academyGroup);
    }

    @Override
    public void SaveChanges() {
        try{
            FileOutputStream file = new FileOutputStream(FILENAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(file);
            
            objectOutputStream.writeObject(academyGroups);

            objectOutputStream.close();
            file.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void LoadFromFile(){
        try{
            FileInputStream file = new FileInputStream(FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(file);

            if (objectInputStream.available() > 0) {
                academyGroups = (List<AcademyGroup>)objectInputStream.readObject();
            }

            objectInputStream.close();
            file.close();
        } catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
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
