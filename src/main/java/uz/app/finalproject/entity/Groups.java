package uz.app.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
