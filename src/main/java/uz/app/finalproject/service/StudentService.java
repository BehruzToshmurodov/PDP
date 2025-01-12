package uz.app.finalproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.app.finalproject.dto.StudentDTO;
import uz.app.finalproject.entity.*;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.repository.AttendanceRepository;
import uz.app.finalproject.repository.GroupRepository;
import uz.app.finalproject.repository.StudentRepository;
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
    final StudentRepository studentRepository;


    public ResponseEntity<?> addStudent(StudentDTO studentDTO) {
        if (studentDTO.getFirstname() == null || studentDTO.getFirstname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("First name is required", null, false));
        }
        if (studentDTO.getLastname() == null || studentDTO.getLastname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Last name is required", null, false));
        }
        if (studentDTO.getPhoneNumber() == null || studentDTO.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Phone number is required", null, false));
        }
        if (studentDTO.getPassword() == null || studentDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Password is required", null, false));
        }


        Student student = new Student();
        student.setFirstname(studentDTO.getFirstname());
        student.setLastname(studentDTO.getLastname());
        student.setGender(studentDTO.getGender());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setPassword(studentDTO.getPassword());
        studentRepository.save(student);


        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Student added to group", studentDTO, true));

    }


    public ResponseEntity<?> getStudents() {
        try {
            List<Student> students = studentRepository.findAllByStatus(Status.ACTIVE);

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
            List<Student> arxiv = studentRepository.findAllByStatus(Status.ARCHIVE);

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


    public ResponseEntity<?> updateStudent(StudentDTO studentDTO, Long id) {
        if (studentDTO.getFirstname() == null || studentDTO.getFirstname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("First name is required", null, false));
        }
        if (studentDTO.getLastname() == null || studentDTO.getLastname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Last name is required", null, false));
        }
        if (studentDTO.getPhoneNumber() == null || studentDTO.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Phone number is required", null, false));
        }
        if (studentDTO.getPassword() == null || studentDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Password is required", null, false));
        }

        try {
            Optional<Student> foundStudent = studentRepository.findById(id);

            if (foundStudent.isPresent()) {
                Student student = foundStudent.get();
                student.setFirstname(studentDTO.getFirstname());
                student.setLastname(studentDTO.getLastname());
                student.setPassword(student.getPassword());
                student.setPhoneNumber(studentDTO.getPhoneNumber());

                studentRepository.save(student);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("Student updated successfully", studentDTO, true));
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Student not found", null, false));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error updating student: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> deleteStudent(Long id) {
        try {

            Optional<Student> byId = studentRepository.findById(id);

            if (byId.isPresent()) {
                Student student = byId.get();

                if (student.getStatus().equals(Status.ACTIVE)) {
                    student.setStatus(Status.ARCHIVE);


                   if ( student.getGroup() != null){
                       student.setGroup(null);
                   }

                    studentRepository.save(student);

                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseMessage("Student successfully deleted and removed from group", null, true));
                } else {
                    return ResponseEntity.status(HttpStatus.OK)
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
            List<Student> result = studentRepository.findAllByFirstnameContainingAndStatusNotOrLastnameContainingAndStatusNot(
                    search, Status.ARCHIVE, search, Status.ARCHIVE);

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

            Optional<Student> byId = studentRepository.findById(id);
            if (byId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("Student not found", null, false));
            }

            Student student = byId.get();


            Optional<Attendance> existingAttendance = attendanceRepository
                    .findByStudentAndAttendanceDate(student, LocalDateTime.now().toLocalDate().atStartOfDay());

            if (existingAttendance.isPresent()) {
                Attendance attendance = existingAttendance.get();
                attendance.setAttended(false);
                attendanceRepository.save(attendance);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("Student marked as not attended", null, true));
            }

            Attendance newAttendance = new Attendance();
            newAttendance.setStudent(student);
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
