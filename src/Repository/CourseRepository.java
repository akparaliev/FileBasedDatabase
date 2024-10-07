package Repository;

import java.util.ArrayList;
import java.util.List;

import Entities.Course;

public class CourseRepository implements IRepository<Course> {
    DbContext context;
    public CourseRepository(DbContext context) {
        this.context = context; 
    }

    @Override
    public List<Course> GetAll() {
        DbSet dbSet = context.GetDatabase();
        return new ArrayList<>(dbSet.getCourses());
    }

    @Override
    public Course GetById(int id) {
       List<Course> courses = GetAll();
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }

        return null;
    }

    @Override
    public void Add(Course course) {
        List<Course> courses = GetAll();
        courses.add(course);
        SaveChanges(courses);
    }

    @Override
    public void Update(Course course) {
        List<Course> courses = GetAll();
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId() == course.getId()) {
                courses.set(i, course);
                break;
            }
        }

        SaveChanges(courses);
    }

    @Override
    public void Remove(int id) {
        List<Course> courses = GetAll();
        courses.removeIf(course -> course.getId() == id);
        SaveChanges(courses);
    }

    private void SaveChanges(List<Course> courses) {
        DbSet dbSet = context.GetDatabase();
        dbSet.setCourses(courses);
        context.SaveChanges(dbSet);
    }
    
}
