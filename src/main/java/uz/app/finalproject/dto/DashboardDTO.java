package uz.app.finalproject.dto;

public class DashboardDTO {

    private Integer staffs;
    private Integer active_students;
    private Integer groups;
    private Integer actively_left_students;


    public DashboardDTO(Integer staffs, Integer active_students, Integer groups, Integer actively_left_students) {
        this.staffs = staffs;
        this.active_students = active_students;
        this.groups = groups;
        this.actively_left_students = actively_left_students;
    }

    public DashboardDTO() {
    }

    public Integer getStaffs() {
        return staffs;
    }

    public void setStaffs(Integer staffs) {
        this.staffs = staffs;
    }

    public Integer getActive_students() {
        return active_students;
    }

    public void setActive_students(Integer active_students) {
        this.active_students = active_students;
    }

    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    public Integer getActively_left_students() {
        return actively_left_students;
    }

    public void setActively_left_students(Integer actively_left_students) {
        this.actively_left_students = actively_left_students;
    }
}
