package uz.app.finalproject.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.app.finalproject.entity.Enums.Gender;
import uz.app.finalproject.entity.Enums.Role;
import uz.app.finalproject.entity.Enums.Status;

import java.util.Collection;
import java.util.List;


@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

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
    @Enumerated
    private Role role;

}
