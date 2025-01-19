package uz.app.finalproject.entity;

import jakarta.persistence.*;

import uz.app.finalproject.entity.Enums.Days;
import uz.app.finalproject.entity.Enums.Status;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;
    private String courseName;
    @ManyToOne
    private User teacher;
    @ManyToOne
    private Room room;
    @ManyToMany
    private List<Student> students;
    private Integer stNumber = 0;
    private List<Days> days;
    private String startTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer groupPrice = 0 ;
    private Status status = Status.ACTIVE;

    public Groups(Long id, String groupName, String courseName, User teacher, Room room, List<Student> students, Integer stNumber, List<Days> days, String startTime, LocalDate startDate, LocalDate endDate, Integer groupPrice, Status status) {
        this.id = id;
        this.groupName = groupName;
        this.courseName = courseName;
        this.teacher = teacher;
        this.room = room;
        this.students = students;
        this.stNumber = stNumber;
        this.days = days;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.groupPrice = groupPrice;
        this.status = status;
    }

    public Groups() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Integer getStNumber() {
        return stNumber;
    }

    public void setStNumber(Integer stNumber) {
        this.stNumber = stNumber;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
