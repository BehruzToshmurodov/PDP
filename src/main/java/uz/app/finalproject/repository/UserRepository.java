package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.finalproject.entity.Enums.Role;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByRoleAndStatusNot(Role role, Status status);

    List<User> findAllByStatus(Status status);

    List<User> findAllByStatusAndRoleNotIn( Status status , List<Role> teacher);

    Integer countByStatus( Status status);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByIdAndRole(Long id , Role role);


}
