package Repository;

import java.util.ArrayList;
import java.util.List;

import Entities.AcademyGroup;

public class AcademyGroupRepository implements IRepository<AcademyGroup> {
    DbContext context;
    public AcademyGroupRepository(DbContext context) {
        this.context = context; 
    }

    @Override
    public List<AcademyGroup> GetAll() {
        DbSet dbSet = context.GetDatabase();
        return new ArrayList<>(dbSet.getAcademyGroups());
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
        academyGroups.removeIf(academyGroup -> academyGroup.getId() == id);
        SaveChanges(academyGroups);
    }

    private void SaveChanges(List<AcademyGroup> academyGroups) {
        DbSet dbSet = context.GetDatabase();
        dbSet.setAcademyGroups(academyGroups);
        context.SaveChanges(dbSet);
    }
}
