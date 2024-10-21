import Entities.Academy;
import Entities.AcademyGroup;
import Entities.Course;
import Entities.Mentor;
import Repository.AcademyGroupRepository;
import Repository.AcademyRepository;
import Repository.CourseRepository;
import Repository.DbContext;
import Repository.MentorRepository;

public class App {
    public static void main(String[] args) throws Exception {
        String fileName = "group.file";
        FillData(fileName);
        PrintData(fileName);
    }

    private static void FillData(String fileName) {
        DbContext database = new DbContext(fileName);
        AcademyRepository academyRepository = new AcademyRepository(database);
        AcademyGroupRepository academyGroupRepository = new AcademyGroupRepository(database);
        CourseRepository courseRepository = new CourseRepository(database);
        MentorRepository mentorRepository = new MentorRepository(database);

        Academy academy = new Academy("GrowthHungry Academy - education for everyone");
        String academyId = academyRepository.Add(academy);

        AcademyGroup group2024 = new AcademyGroup("group 2024", academyId);
        String group2024Id = academyGroupRepository.Add(group2024);
        AcademyGroup group2025 = new AcademyGroup("group 2025", academyId);
        String group2025Id = academyGroupRepository.Add(group2025);

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
}
