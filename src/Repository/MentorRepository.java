package Repository;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import Entities.Mentor;

public class MentorRepository implements IRepository<Mentor> {
           DbContext context;
    public MentorRepository(DbContext context) {
        this.context = context; 
    }

    @Override
    public Map<String,Mentor> GetAll() {
        DbSet dbSet = context.GetDatabase();
        return new HashMap<String,Mentor>(dbSet.getMentors());
    }

    @Override
    public Mentor GetById(String id) {
       Map<String,Mentor> mentors = GetAll();
       if (mentors.containsKey(id)) {
        return mentors.get(id);
       }

       return null;
    }

    @Override
    public String Add(Mentor mentor) {
        DbSet dbSet = context.GetDatabase();

        // add to Mentor table 
        String id = UUID.randomUUID().toString();
        mentor.setId(id); // generate new UUID random string and set it for Id of an entity
        Map<String,Mentor> mentors = new HashMap<String,Mentor>(dbSet.getMentors());
        mentors.put(mentor.getId(), mentor);
        dbSet.setMentors(mentors);
        
        // update academyId index 
        Set<String> indices = dbSet.getMentorsAcademyIndex(mentor.getAcademyId());
        indices.add(id);
        dbSet.setMentorsAcademyIndex(mentor.getAcademyId(), indices);

        context.SaveChanges(dbSet);

        return id;
    }

    @Override
    public void Update(Mentor mentor) {
        DbSet dbSet = context.GetDatabase();

        // Update Mentor table 
        Map<String,Mentor> mentors = new HashMap<String,Mentor>(dbSet.getMentors());
        mentors.put(mentor.getId(), mentor);
        dbSet.setMentors(mentors);

        // update academyId index 
        Set<String> indices = dbSet.getMentorsAcademyIndex(mentor.getAcademyId());
        indices.add(mentor.getId());
        dbSet.setMentorsAcademyIndex(mentor.getAcademyId(), indices);

        context.SaveChanges(dbSet);
    }

    @Override
    public void Remove(String id) {
        DbSet dbSet = context.GetDatabase();

        // Remove from Mentor table
        Map<String,Mentor> mentors = new HashMap<String,Mentor>(dbSet.getMentors());
        String academyId = mentors.get(id).getAcademyId();
        mentors.remove(id);
        dbSet.setMentors(mentors);

        // remove from index
        Set<String> indices = dbSet.getMentorsAcademyIndex(academyId);
        indices.remove(id);
        dbSet.setMentorsAcademyIndex(academyId, indices);

        context.SaveChanges(dbSet);
    }
}
