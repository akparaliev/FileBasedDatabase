package Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Set;

import Entities.MentorsToCourses;

public class MentorsToCoursesRepository implements IRepository<MentorsToCourses> {
    DbContext context;
    public MentorsToCoursesRepository(DbContext context) {
        this.context = context; 
    }

    @Override
    public Map<String,MentorsToCourses> GetAll() {
        DbSet dbSet = context.GetDatabase();
        return new HashMap<String,MentorsToCourses>(dbSet.getMentorsToCourses());
    }

    @Override
    public MentorsToCourses GetById(String id) {
       Map<String,MentorsToCourses> mentorsToCourses = GetAll();
       if (mentorsToCourses.containsKey(id)) {
        return mentorsToCourses.get(id);
       }

       return null;
    }

    @Override
    public String Add(MentorsToCourses mentorsToCourses) {
        DbSet dbSet = context.GetDatabase();

        // add to AcademyGroup table 
        String id = UUID.randomUUID().toString();
        mentorsToCourses.setId(id); // generate new UUID random string and set it for Id of an entity
        Map<String,MentorsToCourses> mentorsToCoursesList = new HashMap<String,MentorsToCourses>(dbSet.getMentorsToCourses());
        mentorsToCoursesList.put(mentorsToCourses.getId(), mentorsToCourses);
        dbSet.setMentorsToCourses(mentorsToCoursesList);
        
        // update courseGroup index 
        Set<String> indices = dbSet.getMentorsToCoursesCourseGroupIndex(mentorsToCourses.getCourseId(), mentorsToCourses.getGroupId());
        indices.add(id);
        dbSet.setMentorsToCoursesCourseGroupIndex(mentorsToCourses.getCourseId(), mentorsToCourses.getGroupId(), indices);

        // update mentorGroup index 
        indices = dbSet.getMentorsToCoursesMentorGroupIndex(mentorsToCourses.getMentorId(), mentorsToCourses.getGroupId());
        indices.add(id);
        dbSet.setMentorsToCoursesMentorGroupIndex(mentorsToCourses.getMentorId(), mentorsToCourses.getGroupId(), indices);

        context.SaveChanges(dbSet);

        return id;
    }

    @Override
    public void Update(MentorsToCourses mentorsToCourses) {
        DbSet dbSet = context.GetDatabase();

        // Update MentorsToCourses table 
        Map<String,MentorsToCourses> mentorsToCoursesList = new HashMap<String,MentorsToCourses>(dbSet.getMentorsToCourses());
        mentorsToCoursesList.put(mentorsToCourses.getId(), mentorsToCourses);
        dbSet.setMentorsToCourses(mentorsToCoursesList);

        // update courseGroup index 
        Set<String> indices = dbSet.getMentorsToCoursesCourseGroupIndex(mentorsToCourses.getCourseId(), mentorsToCourses.getGroupId());
        indices.add(mentorsToCourses.getId());
        dbSet.setMentorsToCoursesCourseGroupIndex(mentorsToCourses.getCourseId(), mentorsToCourses.getGroupId(), indices);

        // update mentorGroup index 
        indices = dbSet.getMentorsToCoursesMentorGroupIndex(mentorsToCourses.getMentorId(), mentorsToCourses.getGroupId());
        indices.add(mentorsToCourses.getId());
        dbSet.setMentorsToCoursesMentorGroupIndex(mentorsToCourses.getMentorId(), mentorsToCourses.getGroupId(), indices);

        context.SaveChanges(dbSet);
    }

    @Override
    public void Remove(String id) {
        DbSet dbSet = context.GetDatabase();

        // Remove from MentorsToCourses table
        Map<String,MentorsToCourses> mentorsToCoursesList = new HashMap<String,MentorsToCourses>(dbSet.getMentorsToCourses());
        String groupId = mentorsToCoursesList.get(id).getGroupId();
        String mentorId = mentorsToCoursesList.get(id).getMentorId();
        String courseId = mentorsToCoursesList.get(id).getCourseId();
        mentorsToCoursesList.remove(id);
        dbSet.setMentorsToCourses(mentorsToCoursesList);

        // remove from courseGroup index 
        Set<String> indices = dbSet.getMentorsToCoursesCourseGroupIndex(courseId, groupId);
        indices.remove(id);
        dbSet.setMentorsToCoursesCourseGroupIndex(courseId, groupId, indices);

        // remove from mentorGroup index 
        indices = dbSet.getMentorsToCoursesMentorGroupIndex(mentorId, groupId);
        indices.remove(id);
        dbSet.setMentorsToCoursesMentorGroupIndex(mentorId, groupId, indices);

        context.SaveChanges(dbSet);
    }
}
