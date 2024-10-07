package Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Entities.Academy;
import Entities.AcademyGroup;
import Entities.Course;
import Entities.Mentor;
import Entities.MentorsToCourses;

public class DbSet implements Serializable{
    private List<Academy> academies;
    private List<AcademyGroup> academyGroups;
    private List<Course> courses;
    private List<Mentor> mentors;
    private List<MentorsToCourses> mentorsToCourses;

    public DbSet() {
        academies = new ArrayList<>();
        academyGroups = new ArrayList<>();
        courses = new ArrayList<>();
        mentors = new ArrayList<>();
        mentorsToCourses = new ArrayList<>();
    }

    public List<Academy> getAcademies() {
        return new ArrayList<>(academies);
    }
    public void setAcademies(List<Academy> academies) {
        this.academies = academies;
    }

    public List<AcademyGroup> getAcademyGroups() {
        return new ArrayList<>(academyGroups);
    }
    public void setAcademyGroups(List<AcademyGroup> academyGroups) {
        this.academyGroups = academyGroups;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Mentor> getMentors() {
        return new ArrayList<>(mentors);
    }
    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
    }

    public List<MentorsToCourses> getMentorsToCourses() {
        return new ArrayList<>(mentorsToCourses);
    }
    public void setMentorsToCourses(List<MentorsToCourses> mentorsToCourses) {
        this.mentorsToCourses = mentorsToCourses;
    } 
}
