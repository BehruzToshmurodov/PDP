package uz.app.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.app.finalproject.entity.Enums.Gender;
import uz.app.finalproject.entity.Enums.Role;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    private Gender gender;
    private Role role;

}
