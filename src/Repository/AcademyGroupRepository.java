package Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Set;

import Entities.AcademyGroup;

public class AcademyGroupRepository implements IRepository<AcademyGroup> {
    DbContext context;
    public AcademyGroupRepository(DbContext context) {
        this.context = context; 
    }

    @Override
    public Map<String,AcademyGroup> GetAll() {
        DbSet dbSet = context.GetDatabase();
        return new HashMap<String,AcademyGroup>(dbSet.getAcademyGroups());
    }

    @Override
    public AcademyGroup GetById(String id) {
       Map<String,AcademyGroup> academyGroups = GetAll();
       if (academyGroups.containsKey(id)) {
        return academyGroups.get(id);
       }

       return null;
    }

    @Override
    public String Add(AcademyGroup academyGroup) {
        DbSet dbSet = context.GetDatabase();

        // add to AcademyGroup table 
        String id = UUID.randomUUID().toString();
        academyGroup.setId(id); // generate new UUID random string and set it for Id of an entity
        Map<String,AcademyGroup> academyGroups = new HashMap<String,AcademyGroup>(dbSet.getAcademyGroups());
        academyGroups.put(academyGroup.getId(), academyGroup);
        dbSet.setAcademyGroups(academyGroups);
        
        // update academyId index 
        Set<String> indices = dbSet.getAcademyGroupAcademyIndex(academyGroup.getAcademyId());
        indices.add(id);
        dbSet.setAcademyGroupAcademyIndex(academyGroup.getAcademyId(), indices);

        context.SaveChanges(dbSet);

        return id;
    }

    @Override
    public void Update(AcademyGroup academyGroup) {
        DbSet dbSet = context.GetDatabase();

        // Update AcademyGroup table 
        Map<String,AcademyGroup> academyGroups = new HashMap<String,AcademyGroup>(dbSet.getAcademyGroups());
        academyGroups.put(academyGroup.getId(), academyGroup);
        dbSet.setAcademyGroups(academyGroups);

        // update academyId index 
        Set<String> indices = dbSet.getAcademyGroupAcademyIndex(academyGroup.getAcademyId());
        indices.add(academyGroup.getId());
        dbSet.setAcademyGroupAcademyIndex(academyGroup.getAcademyId(), indices);

        context.SaveChanges(dbSet);
    }

    @Override
    public void Remove(String id) {
        DbSet dbSet = context.GetDatabase();

        // Remove from AcademyGroup table
        Map<String,AcademyGroup> academyGroups = new HashMap<String,AcademyGroup>(dbSet.getAcademyGroups());
        String academyId = academyGroups.get(id).getAcademyId();
        academyGroups.remove(id);
        dbSet.setAcademyGroups(academyGroups);

        // remove from index
        Set<String> indices = dbSet.getAcademyGroupAcademyIndex(academyId);
        indices.remove(id);
        dbSet.setAcademyGroupAcademyIndex(academyId, indices);

        context.SaveChanges(dbSet);
    }
}
