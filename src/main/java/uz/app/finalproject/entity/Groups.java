package uz.app.finalproject.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.app.finalproject.entity.Enums.Days;
import uz.app.finalproject.entity.Enums.Status;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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

}
