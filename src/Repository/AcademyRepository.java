package Repository;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import Entities.Academy;

public class AcademyRepository implements IRepository<Academy> {
    DbContext context;
    public AcademyRepository(DbContext context) {
        this.context = context; 
    }

    @Override
    public Map<String,Academy> GetAll() {
        DbSet dbSet = context.GetDatabase();
        return new HashMap<String,Academy>(dbSet.getAcademies());
    }

    @Override
    public Academy GetById(String id) {
       Map<String,Academy> academies = GetAll();
        if (academies.containsKey(id)) {
            return academies.get(id);
        }

        return null;
    }

    @Override
    public String Add(Academy academy) {
        DbSet dbSet = context.GetDatabase();
        String id = UUID.randomUUID().toString();
        HashMap<String, Academy> academies = new HashMap<String,Academy>(dbSet.getAcademies());
        
        academies.put(id, academy);

        dbSet.setAcademies(academies);
        context.SaveChanges(dbSet);

        return id;
    }

    @Override
    public void Update(Academy academy) {
        DbSet dbSet = context.GetDatabase();
        HashMap<String, Academy> academies = new HashMap<String,Academy>(dbSet.getAcademies());

        academies.put(academy.getId(), academy);

        dbSet.setAcademies(academies);
        context.SaveChanges(dbSet);
    }

    @Override
    public void Remove(String id) {
        DbSet dbSet = context.GetDatabase();
        HashMap<String, Academy> academies = new HashMap<String,Academy>(dbSet.getAcademies());

        academies.remove(id);

        dbSet.setAcademies(academies);
        context.SaveChanges(dbSet);
    }
}
