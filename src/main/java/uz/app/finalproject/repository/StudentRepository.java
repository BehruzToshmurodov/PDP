package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student , Long> {

    List<Student> findAllByStatus(Status status);

    List<Student> findAllByFirstnameContainingAndStatusNotOrLastnameContainingAndStatusNot(String firstname , Status status , String lastname , Status status1);

    List<Student> findAllByAddedGroupAndStatus(Boolean added, Status status);

    Integer countByStatusNotIn(List<Status> status);

    Integer countByStatus(Status status);

    Optional<Student> findByIdAndStatus(Long id , Status status);

    List<Student> findByStatus(Status status);

    List<Student> findAllByAddedGroupAndStatusNotIn(boolean b, List<Status> status);

    List<Student> findAllByIdInAndStatusIn(List<Long> studentId, List<Status> status);

    List<Student> findAllByAddedGroupAndStatusIn(boolean b, List<Status> archive);

    List<Student> findAllByStatusIn(List<Status> status);
}
