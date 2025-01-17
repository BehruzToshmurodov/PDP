package uz.app.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardDTO {

    private Integer staffs;
    private Integer active_students;
    private Integer groups;
    private Integer actively_left_students;

}