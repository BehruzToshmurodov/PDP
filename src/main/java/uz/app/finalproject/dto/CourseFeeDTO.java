package uz.app.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseFeeDTO {

    private String courseName;
    private Integer course_fee;
    private LocalDate openedDate;

}
