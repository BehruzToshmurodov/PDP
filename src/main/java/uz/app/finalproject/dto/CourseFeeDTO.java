package uz.app.finalproject.dto;

public class CourseFeeDTO {

    private String courseName;
    private Integer course_fee;


    public CourseFeeDTO(String courseName, Integer course_fee) {
        this.courseName = courseName;
        this.course_fee = course_fee;
    }

    public CourseFeeDTO() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourse_fee() {
        return course_fee;
    }

    public void setCourse_fee(Integer course_fee) {
        this.course_fee = course_fee;
    }
}
