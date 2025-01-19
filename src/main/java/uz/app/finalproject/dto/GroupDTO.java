package uz.app.finalproject.dto;


import uz.app.finalproject.entity.Enums.Days;

import java.time.LocalDate;
import java.util.List;

public class GroupDTO {

    private String groupName;
    private String courseName;
    private String teacherId;
    private String roomId;
    private List<Days> days;
    private String startTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer groupPrice;


    public GroupDTO(String groupName, String courseName, String teacherId, String roomId, List<Days> days, String startTime, LocalDate startDate, LocalDate endDate, Integer groupPrice) {
        this.groupName = groupName;
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.roomId = roomId;
        this.days = days;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.groupPrice = groupPrice;
    }

    public GroupDTO() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<Days> getDays() {
        return days;
    }

    public void setDays(List<Days> days) {
        this.days = days;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(Integer groupPrice) {
        this.groupPrice = groupPrice;
    }
}
