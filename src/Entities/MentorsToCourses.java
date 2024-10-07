package Entities;

import java.io.Serializable;

public class MentorsToCourses implements Serializable {
    private int id;
    private int mentorId;
    private int courseId;
    private int groupId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id=id;
    }

    public int getMentorId() {
        return mentorId;
    }
    public void setMentorId(int mentorId) {
        this.mentorId=mentorId;
    }

    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId=courseId;
    }

    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId=groupId;
    }
}
