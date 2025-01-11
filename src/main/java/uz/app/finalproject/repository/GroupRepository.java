package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.Room;
import uz.app.finalproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Groups, Long> {

    List<Groups> findByStatus(String status);

    Boolean existsByGroupNameAndStatusNot(String name, String status);

    List<Groups> findAllByGroupNameContainsAndStatusEquals(String name, String status);

    Optional<Groups> findByStudentsContaining(User user);

    List<Groups> findByRoom(Room room);

    List<Groups> findByTeacher(User user);
}
