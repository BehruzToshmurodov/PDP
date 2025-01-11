package uz.app.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    private String gender;
    private String role;

}
