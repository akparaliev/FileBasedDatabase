package Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import Entities.Academy;
import Entities.AcademyGroup;
import Entities.Course;
import Entities.Mentor;
import Entities.MentorsToCourses;

public class DbSet implements Serializable{
    private Map<Integer,Academy> academies;
    private Map<Integer,AcademyGroup> academyGroups;
    private Map<Integer,Course> courses;
    private Map<Integer,Mentor> mentors;
    private Map<Integer,MentorsToCourses> mentorsToCourses;

    // secondary indexes
    private Map<Integer, Set<Integer>> academyGroupAcademyIndex = new TreeMap<>();

    public DbSet() {
        academies = new HashMap<>();
        academyGroups = new HashMap<>();
        courses = new HashMap<>();
        mentors = new HashMap<>();
        mentorsToCourses = new HashMap<>();
    }

    // Academy
    public Map<Integer,Academy> getAcademies() {
        return new HashMap<>(academies);
    }
    public void setAcademies(Map<Integer,Academy> academies) {
        this.academies = academies;
    }
    public void addAcademy(Academy academy) {
        academies.put(academy.getId(), academy);
    }
    public Academy getAcademy(Integer id) {
        return academies.get(id);
    }

    // AcademyGroup
    public Map<Integer,AcademyGroup> getAcademyGroups() {
        return new HashMap<>(academyGroups);
    }
    public void setAcademyGroups(Map<Integer,AcademyGroup> academyGroups) {
        this.academyGroups = academyGroups;
    }
    public void addAcademyGroup(AcademyGroup academyGroup) {
        academyGroups.put(academyGroup.getId(), academyGroup);
        Set<Integer> indices = academyGroupAcademyIndex.getOrDefault(academyGroup.getAcademyId(), new HashSet<>());
        indices.add(academyGroup.getId());
        academyGroupAcademyIndex.put(academyGroup.getAcademyId(), indices);
    }
    public void removeAcademyGroup(int academyGroupId) {
        academyGroups.remove(academyGroupId);
        Set<Integer> indices = academyGroupAcademyIndex.get(academyGroups.get(academyGroupId).getAcademyId());
        if (indices != null) {
            indices.remove(Integer.valueOf(academyGroupId));
            if (indices.isEmpty()) {
                academyGroupAcademyIndex.remove(academyGroups.get(academyGroupId).getAcademyId());
            }
        }
    }
    public void updateAcademyGroupAcademyId(int academyGroupId, int newAcademyId) {
        AcademyGroup academyGroup = academyGroups.get(academyGroupId);
        Set<Integer> indices = academyGroupAcademyIndex.get(academyGroup.getAcademyId());
        if (indices != null) {
            indices.remove(Integer.valueOf(academyGroupId));
            academyGroupAcademyIndex.remove(academyGroup.getAcademyId());
        }
        Set<Integer> newIndices = academyGroupAcademyIndex.getOrDefault(newAcademyId, new HashSet<>());
        newIndices.add(academyGroupId);
        academyGroupAcademyIndex.put(newAcademyId, newIndices);
    }
    public List<AcademyGroup> getAcademyGroupsByAcademyId(int academyId) {
        List<AcademyGroup> result = new ArrayList<>();
        Set<Integer> indices = academyGroupAcademyIndex.get(academyId);
        if (indices != null) {
            for (int index : indices) {
                result.add(academyGroups.get(index));
            }
        }
        return result;
    }

    public Map<Integer,Course> getCourses() {
        return new HashMap<>(courses);
    }
    public void setCourses(Map<Integer,Course> courses) {
        this.courses = courses;
    }

    public Map<Integer,Mentor> getMentors() {
        return new HashMap<>(mentors);
    }
    public void setMentors(Map<Integer,Mentor> mentors) {
        this.mentors = mentors;
    }

    public Map<Integer,MentorsToCourses> getMentorsToCourses() {
        return new HashMap<>(mentorsToCourses);
    }
    public void setMentorsToCourses(Map<Integer,MentorsToCourses> mentorsToCourses) {
        this.mentorsToCourses = mentorsToCourses;
    } 
}
