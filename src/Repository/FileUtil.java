package Repository;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static void createFileIfNew(String fileName){
        File file = new File(fileName);
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
