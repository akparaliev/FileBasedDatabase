package Repository;

import java.util.ArrayList;
import java.util.List;

import Entities.Academy;

public class AcademyRepository implements IRepository<Academy> {
    DbContext context;
    public AcademyRepository(DbContext context) {
        this.context = context; 
    }

    @Override
    public List<Academy> GetAll() {
        DbSet dbSet = context.GetDatabase();
        return new ArrayList<>(dbSet.getAcademies());
    }

    @Override
    public Academy GetById(int id) {
       List<Academy> academies = GetAll();
        for (Academy academy : academies) {
            if (academy.getId() == id) {
                return academy;
            }
        }

        return null;
    }

    @Override
    public void Add(Academy academy) {
        List<Academy> academies = GetAll();
        academies.add(academy);
        SaveChanges(academies);
    }

    @Override
    public void Update(Academy academy) {
        List<Academy> academies = GetAll();
        for (int i = 0; i < academies.size(); i++) {
            if (academies.get(i).getId() == academy.getId()) {
                academies.set(i, academy);
                break;
            }
        }

        SaveChanges(academies);
    }

    @Override
    public void Remove(int id) {
        List<Academy> academies = GetAll();
        academies.removeIf(academy -> academy.getId() == id);
        SaveChanges(academies);
    }

    private void SaveChanges(List<Academy> academies) {
        DbSet dbSet = context.GetDatabase();
        dbSet.setAcademies(academies);
        context.SaveChanges(dbSet);
    }
    
}
