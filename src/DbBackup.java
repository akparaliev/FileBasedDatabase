import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class DbBackup {
    private final String FILENAME;

    public DbBackup(String fileName) {
        FILENAME = fileName;
    }

    public void RunEvery(int minutes, int seconds) {
        int milliseconds = minutes * 60 * 1000 + seconds * 1000; // Convert minutes and seconds to milliseconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Running backup every " + minutes + " minutes, " + seconds + " seconds...");
                CreateNewVersion();
                RemoveOldVersions();
            }
        }, 0, milliseconds); // Run immediately and then every {minutes} minutes, {seconds} seconds
    } 

    private void CreateNewVersion() {
        try (FileInputStream inputStream = new FileInputStream(FILENAME);
             FileOutputStream outputStream = new FileOutputStream(CreateNewFileName())) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void RemoveOldVersions() {
        /// home task
    }

    private String CreateNewFileName() {
        LocalDateTime now = LocalDateTime.now();
        
        // Format timestamp as desired (e.g., "YYYY-MM-dd HH:mm:ss") 2024-10-21 23:32:05
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convert timestamp to string
        String timestampString = now.format(formatter);
        return FILENAME + "_" + timestampString; // db.file_2024-10-21 23:32:05
    }
}
