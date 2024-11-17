package Entities;

import java.io.Serializable;

public class AcademyGroup implements Serializable{
    private String id;
    private String groupName;
    private String academyId;

    public AcademyGroup(String groupName, String academyId){
        this.groupName = groupName;
        this.academyId = academyId;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id=id;
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName=groupName;
    }

    public String getAcademyId() {
        return academyId;
    }
    public void setAcademyId(String academyId) {
        this.academyId=academyId;
    }

    @Override
    public String toString() {
        return getId() + " " + getGroupName();
    }
}
