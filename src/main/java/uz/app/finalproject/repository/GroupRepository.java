package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.Room;
import uz.app.finalproject.entity.User;

import java.util.List;


public interface GroupRepository extends JpaRepository<Groups, Long> {

    List<Groups> findAllByStatus(Status status);

    Boolean existsByGroupNameAndStatusNot(String name, Status status);

    List<Groups> findAllByGroupNameContainsAndStatusEquals(String name, Status status);

    List<Groups> findAllByRoom(Room room);

    List<Groups> findByTeacher(User user);

    Groups findByStudentsId(Long id);

    List<Groups> findAllByTeacherAndStatus(User id , Status status);

    Integer countByStatus(Status status);

    List<Groups> findByTeacherId(Long teacherId);

}

