package uz.app.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDTO {

    private String roomName;
    private Integer capacity;
    private Integer countOfTable;
    private Integer countOfChair;

}
