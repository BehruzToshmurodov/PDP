package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.finalproject.entity.Attendance;
import uz.app.finalproject.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance , Long> {

    Optional<Attendance> findByUserAndAttendanceDate(User user , LocalDateTime localDateTime);

    List<Attendance> findAllByUserIn(List<User> students);
}
