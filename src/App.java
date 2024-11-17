import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

import Entities.Academy;
import Entities.AcademyGroup;
import Entities.Course;
import Entities.Mentor;
import Repository.AcademyGroupRepository;
import Repository.AcademyRepository;
import Repository.CourseRepository;
import Repository.DbContext;
import Repository.MentorRepository;
import Repository.OperationType;
import Repository.RepositoryDecorator;
import Repository.RepositoryWALDecorator;
import Repository.WriteAheadLogEntry;

public class App {
    public static void main(String[] args) throws Exception {
        String dfFileName = "db.file";
        String walFileName = "wal.json";
        FillData(dfFileName, walFileName);
        //PrintData(dfFileName);
        PrintFromWAL(walFileName);
        System.in.read();
    }

    private static void FillData(String dbFileName, String walFileName) {
        DbContext database = new DbContext(dbFileName);
        RepositoryDecorator<Academy> academyRepository = new RepositoryWALDecorator<Academy>(new AcademyRepository(database), walFileName);
        RepositoryDecorator<AcademyGroup> academyGroupRepository = new RepositoryWALDecorator<AcademyGroup>(new AcademyGroupRepository(database), walFileName);
        RepositoryDecorator<Course> courseRepository = new RepositoryWALDecorator<Course>(new CourseRepository(database), walFileName);
        RepositoryDecorator<Mentor> mentorRepository = new RepositoryWALDecorator<Mentor>(new MentorRepository(database), walFileName);

        Academy academy = new Academy("GrowthHungry Academy 1");
        String academyId = academyRepository.Add(academy);

        AcademyGroup group2024 = new AcademyGroup("group 2024", academyId);
        academyGroupRepository.Add(group2024);
        group2024.setGroupName("group 2024 is the best");
        academyGroupRepository.Update(group2024);
        AcademyGroup group2025 = new AcademyGroup("group 2025", academyId);
        String group2025Id = academyGroupRepository.Add(group2025);
        academyGroupRepository.Remove(group2025Id);

        Course csCourse = new Course("Computer Science", academyId);
        String courseId = courseRepository.Add(csCourse);
        Course algoCourse = new Course("Algorithms", academyId);
        String algoCourseId = courseRepository.Add(algoCourse);

        Mentor nurbekMentor = new Mentor("Nurbek Akparaliev", academyId);
        String nurbekMentorId = mentorRepository.Add(nurbekMentor);
        Mentor dastanMentor = new Mentor("Dastan Telnov", academyId);
        String dastanMentorId = mentorRepository.Add(dastanMentor);
        Mentor scottMentor = new Mentor("Scott Miles", academyId);
        String scottMentorId = mentorRepository.Add(scottMentor);
    }

    private static void PrintData(String fileName) {
        DbContext database = new DbContext(fileName);
        AcademyRepository academyRepository = new AcademyRepository(database);
        AcademyGroupRepository academyGroupRepository = new AcademyGroupRepository(database);
        CourseRepository courseRepository = new CourseRepository(database);
        MentorRepository mentorRepository = new MentorRepository(database);

        System.out.println("All Academies information: ");
        for (Academy academy : academyRepository.GetAll().values()) {
            System.out.println(academy.getDescription());
        }

        System.out.println("All groups names: ");
        for (AcademyGroup academyGroup : academyGroupRepository.GetAll().values()) {
            System.out.println(academyGroup.getGroupName());
        }

        System.out.println("All courses: ");
        for (Course course : courseRepository.GetAll().values()) {
            System.out.println(course.getName());
        }

        System.out.println("All mentors: ");
        for (Mentor mentor : mentorRepository.GetAll().values()) {
            System.out.println(mentor.getName());
        }
    }

    private static void PrintFromWAL(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Gson gson = new Gson();

            while ((line = reader.readLine()) != null) {
                WriteAheadLogEntry<?> entry = gson.fromJson(line, WriteAheadLogEntry.class);

                System.out.print("Operation: " + entry.getOperationType());
                System.out.print(", class name: " + entry.getClassName());
                switch (entry.getOperationType()) {
                    case OperationType.UPDATE:
                        System.out.println(", entity: " + entry.getEntity().toString());
                        break;
                    case OperationType.ADD:
                        System.out.print(", id: " + entry.getId());
                        System.out.println(", entity: " + entry.getEntity().toString());
                        break;
                    case OperationType.REMOVE:
                        System.out.println(", id: " + entry.getId());
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
