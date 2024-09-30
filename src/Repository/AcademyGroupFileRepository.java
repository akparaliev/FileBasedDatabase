package Repository;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import Entities.AcademyGroup;

public class AcademyGroupFileRepository implements IRepository<AcademyGroup> {
    private final String FILENAME;
    
    public AcademyGroupFileRepository(String filename){
        FILENAME = filename;
        createFileIfNew();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AcademyGroup> GetAll() {
        List<AcademyGroup> academyGroups = new ArrayList<>();
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILENAME))){
                academyGroups = (List<AcademyGroup>) objectInputStream.readObject(); 
        } catch (EOFException e) {
           
        } 
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return academyGroups;
    }

    @Override
    public AcademyGroup GetById(int id) {
        List<AcademyGroup> academyGroups = GetAll();
        for (AcademyGroup academyGroup : academyGroups) {
            if (academyGroup.getId() == id) {
                return academyGroup;
            }
        }

        return null;
    }

    @Override
    public void Add(AcademyGroup academyGroup) {
        List<AcademyGroup> academyGroups = GetAll();
        academyGroups.add(academyGroup);
        SaveChanges(academyGroups);
    }

    @Override
    public void Update(AcademyGroup academyGroup) {
        List<AcademyGroup> academyGroups = GetAll();
        for (int i = 0; i < academyGroups.size(); i++) {
            if (academyGroups.get(i).getId() == academyGroup.getId()) {
                academyGroups.set(i, academyGroup);
                break;
            }
        }

        SaveChanges(academyGroups);
    }

    @Override
    public void Remove(int id) {
        List<AcademyGroup> academyGroups = GetAll();
        academyGroups.removeIf(group -> group.getId() == id);
        SaveChanges(academyGroups);
    }

    private void SaveChanges(List<AcademyGroup> academyGroups) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            objectOutputStream.writeObject(academyGroups); 
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
