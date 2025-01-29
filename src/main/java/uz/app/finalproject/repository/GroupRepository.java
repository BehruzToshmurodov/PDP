package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.Room;
import uz.app.finalproject.entity.Student;
import uz.app.finalproject.entity.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


public interface GroupRepository extends JpaRepository<Groups, Long> {

    List<Groups> findAllByStatus(Status status);

    Boolean existsByGroupNameAndStatusNot(String name, Status status);

    List<Groups> findAllByGroupNameContainsAndStatusEquals(String name, Status status);

    List<Groups> findAllByRoom(Room room);

    List<Groups> findByTeacher(User user);

    Groups findByStudentsId(Long id);

    List<Groups> findAllByTeacher(User id);

    Integer countByStatus(Status status);

    List<Groups> findByTeacherId(Long teacherId);

}

