package uz.app.finalproject.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.app.finalproject.entity.Enums.Days;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupDTO {

    private String groupName;
    private String courseName;
    private Long teacherId;
    private Long roomId;
    private List<Days> days;
    private String startTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer groupPrice;

}
