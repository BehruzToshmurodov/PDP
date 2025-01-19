package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.finalproject.entity.GroupAttendance;
import uz.app.finalproject.entity.Groups;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

public interface GroupAttendanceRepository extends JpaRepository< GroupAttendance , Long> {

   Optional<GroupAttendance> findByGroupsAndDate(Groups groups , LocalDate date);

   List<GroupAttendance> findByGroups(Groups group);

}
