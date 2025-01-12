package uz.app.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;
    @ManyToOne
    private User teacher;
    @ManyToOne
    private Room room;
    private Integer stNumber = 0;
    private String days;
    private String startTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double groupPrice;
    private String status = "ACTIVE";


}
