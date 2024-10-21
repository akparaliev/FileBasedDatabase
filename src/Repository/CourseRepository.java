package Repository;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import Entities.Course;

public class CourseRepository implements IRepository<Course> {
    DbContext context;
    public CourseRepository(DbContext context) {
        this.context = context; 
    }

    @Override
    public Map<String,Course> GetAll() {
        DbSet dbSet = context.GetDatabase();
        return new HashMap<String,Course>(dbSet.getCourses());
    }

    @Override
    public Course GetById(String id) {
       Map<String,Course> courses = GetAll();
       if (courses.containsKey(id)) {
        return courses.get(id);
       }

       return null;
    }

    @Override
    public String Add(Course course) {
        DbSet dbSet = context.GetDatabase();

        // add to Course table 
        String id = UUID.randomUUID().toString();
        course.setId(id); // generate new UUID random string and set it for Id of an entity
        Map<String,Course> courses = new HashMap<String,Course>(dbSet.getCourses());
        courses.put(course.getId(), course);
        dbSet.setCourses(courses);
        
        // update academyId index 
        Set<String> indices = dbSet.getCoursesAcademyIndex(course.getAcademyId());
        indices.add(id);
        dbSet.setCoursesAcademyIndex(course.getAcademyId(), indices);

        context.SaveChanges(dbSet);

        return id;
    }

    @Override
    public void Update(Course course) {
        DbSet dbSet = context.GetDatabase();

        // Update Course table 
        Map<String,Course> courses = new HashMap<String,Course>(dbSet.getCourses());
        courses.put(course.getId(), course);
        dbSet.setCourses(courses);

        // update academyId index 
        Set<String> indices = dbSet.getCoursesAcademyIndex(course.getAcademyId());
        indices.add(course.getId());
        dbSet.setCoursesAcademyIndex(course.getAcademyId(), indices);

        context.SaveChanges(dbSet);
    }

    @Override
    public void Remove(String id) {
        DbSet dbSet = context.GetDatabase();

        // Remove from Course table
        Map<String,Course> courses = new HashMap<String,Course>(dbSet.getCourses());
        String academyId = courses.get(id).getAcademyId();
        courses.remove(id);
        dbSet.setCourses(courses);

        // remove from index
        Set<String> indices = dbSet.getCoursesAcademyIndex(academyId);
        indices.remove(id);
        dbSet.setCoursesAcademyIndex(academyId, indices);

        context.SaveChanges(dbSet);
    }
    
}
