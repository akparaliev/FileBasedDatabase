import Entities.AcademyGroup;
import Repository.AcademyGroupRepository;

public class App {
    public static void main(String[] args) throws Exception {
        AcademyGroupRepository groupRepository = new AcademyGroupRepository("group.file");
        groupRepository.LoadFromFile();
        groupRepository.Add(new AcademyGroup(0, "Group 2024"));
        groupRepository.SaveChanges();

        groupRepository.LoadFromFile();
        for(AcademyGroup group: groupRepository.GetAll()){
            System.out.println(group.getId() + ", " + group.getGroupName());
        }
    }
}
