package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student , Long> {
    List<Student> findAllByStatus(Status status);

    List<Student> findAllByFirstnameContainingOrLastnameContainingAndStatusNot(String firstname, String lastname, Status status);

    List<Student> findAllByFirstnameContainingAndStatusNotOrLastnameContainingAndStatusNot(String firstname , Status status , String lastname , Status status1);

    List<Student> findAllByGroupAndStatus(Groups groups, Status status);
}