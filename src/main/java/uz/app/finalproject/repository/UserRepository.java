package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.finalproject.entity.Enums.Role;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByFirstnameAndLastname(String firstname, String lastName);

    List<User> findAllByRoleAndStatusNot(Role role, Status status);

    List<User> findAllByRoleNotInAndStatus(List<Role> roles, Status status);

    List<User> findAllByFirstnameContainingOrLastnameContainingAndStatusAndRole(String firstname, String lastname, Status status, Role role);

    List<User> findAllByRoleInAndStatus(List<Role> cleaner, Status arxiv);

    List<User> findAllByRoleAndStatus(Role student, Status archive);

    List<User> findAllByRoleNotAndStatusNot(Role role, Status status);

    List<User> findAllByStatus(Status status);

    List<User> findAllByStatusAndRoleNotIn( Status status , List<Role> teacher);
}
