package uz.app.finalproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.finalproject.dto.UserDTO;
import uz.app.finalproject.entity.Attendance;
import uz.app.finalproject.entity.Enums.Gender;
import uz.app.finalproject.entity.Enums.Role;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.entity.User;
import uz.app.finalproject.repository.AttendanceRepository;
import uz.app.finalproject.repository.GroupRepository;
import uz.app.finalproject.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    final UserRepository userRepository;
    final GroupRepository groupRepository;
    final AttendanceRepository attendanceRepository;


    public ResponseEntity<?> addStudent(@RequestBody UserDTO userDto, @PathVariable String groupId) {
        if (userDto.getFirstname() == null || userDto.getFirstname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("First name is required", null, false));
        }
        if (userDto.getLastname() == null || userDto.getLastname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Last name is required", null, false));
        }
        if (userDto.getPhoneNumber() == null || userDto.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Phone number is required", null, false));
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Password is required", null, false));
        }

        try {
            User user = new User();
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setGender(Gender.valueOf(userDto.getGender()));
            user.setPhoneNumber(String.valueOf(userDto.getPhoneNumber()));
            user.setRole(Role.STUDENT);
            user.setPassword(userDto.getPassword());

            Optional<Groups> byId = groupRepository.findById(Long.valueOf(groupId));

            if (byId.isPresent()) {
                Groups groups = byId.get();

                userRepository.save(user);

                groups.getStudents().add(user);
                groups.setStNumber(groups.getStudents().size());
                groupRepository.save(groups);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("Student added to group", userDto, true));
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Group not found", null, false));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error adding student to group: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> getStudent() {
        try {
            List<User> students = userRepository.findAllByRoleAndStatus(Role.STUDENT, Status.ARCHIVE);

            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("No active students found", students, true));
            }

            return ResponseEntity.ok(new ResponseMessage("All students", students, true));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error retrieving students: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> getArxiv() {
        try {
            List<User> arxiv = userRepository.findAllByRoleAndStatus(Role.STUDENT, Status.ARCHIVE);

            if (arxiv.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("No archived users found", arxiv, true));
            }

            return ResponseEntity.ok(new ResponseMessage("All archived users", arxiv, true));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error retrieving archived users: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> updateStudent(UserDTO userDto, Integer id) {
        if (userDto.getFirstname() == null || userDto.getFirstname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("First name is required", null, false));
        }
        if (userDto.getLastname() == null || userDto.getLastname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Last name is required", null, false));
        }
        if (userDto.getPhoneNumber() == null || userDto.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Phone number is required", null, false));
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Password is required", null, false));
        }

        try {
            Optional<User> student = userRepository.findById(Long.valueOf(id));

            if (student.isPresent()) {
                User user = student.get();
                user.setFirstname(userDto.getFirstname());
                user.setLastname(userDto.getLastname());
                user.setPassword(userDto.getPassword());
                user.setRole(Role.valueOf(userDto.getRole()));
                user.setPhoneNumber(String.valueOf(userDto.getPhoneNumber()));

                userRepository.save(user);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("Student updated successfully", userDto, true));
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Student not found", null, false));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error updating student: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> deleteStudent(String id) {
        try {
            Long studentId = Long.valueOf(id);
            Optional<User> byId = userRepository.findById(studentId);

            if (byId.isPresent()) {
                User user = byId.get();

                if (user.getStatus().equals(Status.ACTIVE)) {
                    user.setStatus(Status.ARCHIVE);
                    userRepository.save(user);

                    Optional<Groups> groupOptional = groupRepository.findByStudentsContaining(user);
                    if (groupOptional.isPresent()) {
                        Groups group = groupOptional.get();
                        group.getStudents().remove(user);
                        group.setStNumber(group.getStudents().size());
                        groupRepository.save(group);
                    }

                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseMessage("Student successfully deleted and removed from group", null, true));
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(new ResponseMessage("Student already archived", null, false));
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Student not found", null, false));

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid student ID format", null, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error deleting student: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> search(String search) {
        try {
            List<User> result = userRepository.findAllByFirstnameContainingOrLastnameContainingAndStatusAndRole(search, search, Status.ACTIVE, Role.STUDENT);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("No active students found", result, true));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Search results", result, true));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error during search: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> attendance(Long id) {
        try {

            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Student not found", null, false));
            }

            User user = userOptional.get();


            Optional<Attendance> existingAttendance = attendanceRepository
                    .findByUserAndAttendanceDate(user, LocalDateTime.now().toLocalDate().atStartOfDay());

            if (existingAttendance.isPresent()) {
                Attendance attendance = existingAttendance.get();
                attendance.setAttended(false);
                attendanceRepository.save(attendance);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("Student marked as not attended", null, true));
            }

            Attendance newAttendance = new Attendance();
            newAttendance.setUser(user);
            newAttendance.setAttendanceDate(LocalDateTime.now());
            newAttendance.setAttended(true);
            attendanceRepository.save(newAttendance);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage("Student attendance recorded as attended", null, true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error processing attendance: " + e.getMessage(), null, false));
        }

    }
}
