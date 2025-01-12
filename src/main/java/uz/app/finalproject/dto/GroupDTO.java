package uz.app.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupDTO {

    private String groupName;
    private String teacherId;
    private String roomId;
    private String days;
    private String startTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double groupPrice;


}
