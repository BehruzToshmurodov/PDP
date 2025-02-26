package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.finalproject.entity.Attendance;
import uz.app.finalproject.entity.Student;
import uz.app.finalproject.entity.User;

import javax.xml.stream.events.StartDocument;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance , Long> {

    List<Attendance> findAllByStudentIn(List<Student> students);

    Optional<Attendance> findByStudentAndAttendanceDate(Student student, LocalDate today);

}
