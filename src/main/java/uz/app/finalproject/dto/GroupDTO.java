package uz.app.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupDTO {

    private String groupName;
    private TeacherDTO teacher;
    private String room;
    private String days;
    private String startTime;

}
