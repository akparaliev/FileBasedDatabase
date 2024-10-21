package Entities;

import java.io.Serializable;

public class MentorsToCourses implements Serializable {
    private String id;
    private String mentorId;
    private String courseId;
    private String groupId;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id=id;
    }

    public String getMentorId() {
        return mentorId;
    }
    public void setMentorId(String mentorId) {
        this.mentorId=mentorId;
    }

    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId=courseId;
    }

    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId=groupId;
    }
}
