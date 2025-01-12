package uz.app.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.app.finalproject.entity.Enums.Gender;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {

    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    private Gender gender;

}