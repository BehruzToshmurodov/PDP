package uz.app.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;
import uz.app.finalproject.entity.Enums.Gender;
import uz.app.finalproject.entity.Enums.Status;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    @Enumerated
    private Gender gender;
    @Enumerated
    private Status status = Status.ACTIVE;
    @ManyToOne
    private Groups group;

}
