package Repository;

import java.util.ArrayList;
import java.util.List;

import Entities.MentorsToCourses;

public class MentorsToCoursesRepository implements IRepository<MentorsToCourses> {
    DbContext context;
    public MentorsToCoursesRepository(DbContext context) {
        this.context = context; 
    }

    @Override
    public List<MentorsToCourses> GetAll() {
        DbSet dbSet = context.GetDatabase();
        return new ArrayList<>(dbSet.getMentorsToCourses());
    }

    @Override
    public MentorsToCourses GetById(int id) {
       List<MentorsToCourses> mentorsToCourses = GetAll();
        for (MentorsToCourses mentorsToCourse : mentorsToCourses) {
            if (mentorsToCourse.getId() == id) {
                return mentorsToCourse;
            }
        }

        return null;
    }

    @Override
    public void Add(MentorsToCourses mentorsToCourses) {
        List<MentorsToCourses> mentorsToCoursesList = GetAll();
        mentorsToCoursesList.add(mentorsToCourses);
        SaveChanges(mentorsToCoursesList);
    }

    @Override
    public void Update(MentorsToCourses mentorsToCourses) {
        List<MentorsToCourses> mentorsToCoursesList = GetAll();
        for (int i = 0; i < mentorsToCoursesList.size(); i++) {
            if (mentorsToCoursesList.get(i).getId() == mentorsToCourses.getId()) {
                mentorsToCoursesList.set(i, mentorsToCourses);
                break;
            }
        }

        SaveChanges(mentorsToCoursesList);
    }

    @Override
    public void Remove(int id) {
        List<MentorsToCourses> mentorsToCoursesList = GetAll();
        mentorsToCoursesList.removeIf(mentorsToCourses -> mentorsToCourses.getId() == id);
        SaveChanges(mentorsToCoursesList);
    }

    private void SaveChanges(List<MentorsToCourses> mentorsToCoursesList) {
        DbSet dbSet = context.GetDatabase();
        dbSet.setMentorsToCourses(mentorsToCoursesList);
        context.SaveChanges(dbSet);
    }
}
