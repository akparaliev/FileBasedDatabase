package Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javafx.util.Pair;

import Entities.Academy;
import Entities.AcademyGroup;
import Entities.Course;
import Entities.Mentor;
import Entities.MentorsToCourses;

public class DbSet implements Serializable{
    private Map<String,Academy> academies;
    private Map<String,AcademyGroup> academyGroups;
    private Map<String,Course> courses;
    private Map<String,Mentor> mentors;
    private Map<String,MentorsToCourses> mentorsToCourses;

    // secondary indexes
    private Map<String, Set<String>> academyGroupsAcademyIndex;
    private Map<String, Set<String>> coursesAcademyIndex;
    private Map<String, Set<String>> mentorsAcademyIndex;

    private Map<Pair<String,String>, Set<String>> courseGroupIndex;
    private Map<Pair<String,String>, Set<String>> mentorGroupIndex;


    public DbSet() {
        academies = new HashMap<>();
        academyGroups = new HashMap<>();
        courses = new HashMap<>();
        mentors = new HashMap<>();
        mentorsToCourses = new HashMap<>();
        academyGroupsAcademyIndex = new TreeMap<>();
        coursesAcademyIndex = new TreeMap<>();
        mentorsAcademyIndex = new TreeMap<>();
        courseGroupIndex = new TreeMap<>();
        mentorGroupIndex = new TreeMap<>();
    }

    // Academy
    public Map<String,Academy> getAcademies() {
        return new HashMap<>(academies);
    }
    public void setAcademies(Map<String,Academy> academies) {
        this.academies = academies;
    }

    // AcademyGroup
    public Map<String,AcademyGroup> getAcademyGroups() {
        return new HashMap<>(academyGroups);
    }
    public void setAcademyGroups(Map<String,AcademyGroup> academyGroups) {
        this.academyGroups = academyGroups;
    }
    public Set<String> getAcademyGroupAcademyIndex(String academyId) {
        if (this.academyGroupsAcademyIndex.containsKey(academyId)) {
            return new HashSet<>(this.academyGroupsAcademyIndex.get(academyId));
        }

        return new HashSet<>();
    }

    public void setAcademyGroupAcademyIndex(String academyId, Set<String> academyGroups) {
        this.academyGroupsAcademyIndex.put(academyId, academyGroups);
    }
    public List<AcademyGroup> getAcademyGroupsByAcademyId(String academyId) {
        List<AcademyGroup> result = new ArrayList<>();
        Set<String> indices = academyGroupsAcademyIndex.get(academyId);
        if (indices != null) {
            for (String index : indices) {
                result.add(academyGroups.get(index));
            }
        }
        return result;
    }

    // Course
    public Map<String,Course> getCourses() {
        return new HashMap<>(courses);
    }
    public void setCourses(Map<String,Course> courses) {
        this.courses = courses;
    }
    public Set<String> getCoursesAcademyIndex(String academyId) {
        if (this.coursesAcademyIndex.containsKey(academyId)) {
            return new HashSet<>(this.coursesAcademyIndex.get(academyId));
        }

        return new HashSet<>();
    }
    public void setCoursesAcademyIndex(String academyId, Set<String> academyGroups) {
        this.coursesAcademyIndex.put(academyId, academyGroups);
    }
    public List<Course> getCoursesByAcademyId(String academyId) {
        List<Course> result = new ArrayList<>();
        Set<String> indices = coursesAcademyIndex.get(academyId);
        if (indices != null) {
            for (String index : indices) {
                result.add(courses.get(index));
            }
        }
        return result;
    }

    // Mentor
    public Map<String,Mentor> getMentors() {
        return new HashMap<>(mentors);
    }
    public void setMentors(Map<String,Mentor> mentors) {
        this.mentors = mentors;
    }
    public Set<String> getMentorsAcademyIndex(String academyId) {
        if (this.mentorsAcademyIndex.containsKey(academyId)) {
            return new HashSet<>(this.mentorsAcademyIndex.get(academyId));
        }

        return new HashSet<>();
    }
    public void setMentorsAcademyIndex(String academyId, Set<String> academyGroups) {
        this.mentorsAcademyIndex.put(academyId, academyGroups);
    }
    public List<Mentor> getMentorsByAcademyId(String academyId) {
        List<Mentor> result = new ArrayList<>();
        Set<String> indices = mentorsAcademyIndex.get(academyId);
        if (indices != null) {
            for (String index : indices) {
                result.add(mentors.get(index));
            }
        }
        return result;
    }

    // MentorsToCourses
    public Map<String,MentorsToCourses> getMentorsToCourses() {
        return new HashMap<>(mentorsToCourses);
    }
    public void setMentorsToCourses(Map<String,MentorsToCourses> mentorsToCourses) {
        this.mentorsToCourses = mentorsToCourses;
    }
    public Set<String> getMentorsToCoursesCourseGroupIndex(String courseId, String groupId) {
        return new HashSet<>(this.courseGroupIndex.get(new Pair<>(courseId,groupId)));
    }
    public void setMentorsToCoursesCourseGroupIndex(String courseId, String groupId, Set<String> mentorsToCourses) {
        this.courseGroupIndex.put(new Pair<>(courseId,groupId), mentorsToCourses);
    }
    public Set<String> getMentorsToCoursesMentorGroupIndex(String mentorId, String groupId) {
        return new HashSet<>(this.mentorGroupIndex.get(new Pair<>(mentorId, groupId)));
    }
    public void setMentorsToCoursesMentorGroupIndex(String mentorId, String groupId, Set<String> mentorsToCourses) {
        this.mentorGroupIndex.put(new Pair<>(mentorId, groupId), mentorsToCourses);
    }

    // give me all mentors names in CS course and in group 2024
    // result: Nurbek, Scott
    // process: courseGroupIndex contains pair of courseId, groupId as a key, a value it is set of mentorsToCourses(id, courseId, groupId, mentorId)
    public List<Mentor> getAllMentorsByCourseIdAndGroupId(String courseId, String groupId) {
        List<Mentor> result = new ArrayList<>();
        Set<String> indices = courseGroupIndex.get(new Pair<>(courseId, groupId));
        if (indices != null) {
            for (String index : indices) {
                MentorsToCourses mentorsToCourses = this.mentorsToCourses.get(index);
                result.add(mentors.get(mentorsToCourses.getMentorId()));
            }
        }
        return result;
    }

    // give me all courses where this mentor teaches and group id is {groupId} , Azret + group2024
    // result: Algorithms, Math
    // process: mentorGroupIndex contains pair of mentorId, groupId as a key, a value it is set of mentorsToCourses(id, courseId, groupId, mentorId)
    public List<Course> getAllCoursesByMentorIdAndGroupId(String mentorId, String groupId) {
        List<Course> result = new ArrayList<>();
        Set<String> indices = mentorGroupIndex.get(new Pair<>(mentorId, groupId));
        if (indices != null) {
            for (String index : indices) {
                MentorsToCourses mentorsToCourses = this.mentorsToCourses.get(index);
                result.add(courses.get(mentorsToCourses.getCourseId()));
            }
        }
        return result;
    }
}
