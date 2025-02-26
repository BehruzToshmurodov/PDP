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

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor

public class StudentService {

    final AttendanceRepository attendanceRepository;

    final StudentRepository studentRepository;

    final GroupRepository groupRepository;


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



        Student student = new Student();
        student.setFirstname(studentDTO.getFirstname());
        student.setLastname(studentDTO.getLastname());
        student.setGender(studentDTO.getGender());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        studentRepository.save(student);


        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Student added to group", studentDTO, true));

    }


    public ResponseEntity<?> getStudents() {
        try {

            List<Student> students = studentRepository.findAllByStatus(Status.ACTIVE);

            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("No active students found", List.of(), true));
            }

            List<Map<String, Object>> studentDataList = new ArrayList<>();

            for (Student student : students) {

                Groups group = groupRepository.findByStudentsId(student.getId());

                Map<String, Object> studentData = new HashMap<>();
                studentData.put("student", student);
                if (group != null) {
                    studentData.put("groupName", group.getGroupName());
                    studentData.put("courseName", group.getCourseName());
                } else {
                    studentData.put("groupName", null);
                    studentData.put("courseName", null);
                }
                studentDataList.add(studentData);
            }

            return ResponseEntity.ok(new ResponseMessage("All students with group info", studentDataList, true));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Error retrieving students: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> getArxiv() {
        try {
            List<Student> arxiv = studentRepository.findAllByStatusIn(List.of(Status.ARCHIVE , Status.ACTIVELY_LEFT));

            if (arxiv.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("No archived users found", arxiv, true));
            }

            return ResponseEntity.ok(new ResponseMessage("All archived users", arxiv, true));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
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


        try {
            Optional<Student> foundStudent = studentRepository.findById(id);

            if (foundStudent.isPresent()) {
                Student student = foundStudent.get();
                student.setFirstname(studentDTO.getFirstname());
                student.setLastname(studentDTO.getLastname());
                student.setPhoneNumber(studentDTO.getPhoneNumber());

                studentRepository.save(student);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("Student updated successfully", studentDTO, true));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Student not found", null, false));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Error updating student: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> deleteStudent(Long id) {
        try {

            Optional<Student> byId = studentRepository.findById(id);

            if (byId.isPresent()) {
                Student student = byId.get();

                Groups byStudentsId = groupRepository.findByStudentsId(student.getId());

                if (byStudentsId != null) {
                    byStudentsId.getStudents().remove(student);
                    byStudentsId.setStNumber(byStudentsId.getStNumber() - 1);
                    groupRepository.save(byStudentsId);
                }

                if (student.getStatus().equals(Status.ACTIVE)) {

                    student.setStatus(Status.ACTIVELY_LEFT);
                    student.setAddedGroup(false);
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

    public ResponseEntity<?> attendance(List<Long> studentIds, Long groupId) {

        Optional<Groups> optionalGroups = groupRepository.findById(groupId);

        if (optionalGroups.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Group not found", null, false));
        }

        Groups groups = optionalGroups.get();
        List<Student> groupStudents = groups.getStudents();

        if (groupStudents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("No students in this group", null, false));
        }


        Set<Long> attendedStudentIds = new HashSet<>(studentIds);

        LocalDate today = LocalDate.now();

        for (Student groupStudent : groupStudents) {

            boolean isAttended = attendedStudentIds.contains(groupStudent.getId());


            Optional<Attendance> existingAttendance = attendanceRepository.findByStudentAndAttendanceDate(groupStudent, today);

            Attendance attendance;
            if (existingAttendance.isPresent()) {

                attendance = existingAttendance.get();
                attendance.setAttended(isAttended);
            } else {

                attendance = new Attendance();
                attendance.setStudent(groupStudent);
                attendance.setAttendanceDate(today);
                attendance.setAttended(isAttended);
            }
            attendanceRepository.save(attendance);
        }

        return ResponseEntity.ok(new ResponseMessage("Students attendance saved successfully", null, true));
    }

    public ResponseEntity<?> findStudentById(Long id) {

        Optional<Student> byId = studentRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Student not found", null, false));
        }

        Student student = byId.get();

        Groups group = groupRepository.findByStudentsId(id);

        if (group != null) {
            group.setStudents(null);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("student", student);
        response.put("group", group);

        return ResponseEntity.ok(new ResponseMessage("Success", response, true));
    }

    public ResponseEntity<?> stopStudent(Long studentId) {

        Optional<Student> byId = studentRepository.findById(studentId);

        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Student not found", null, false));
        }

        Student student = byId.get();

        Groups byStudentsId = groupRepository.findByStudentsId(student.getId());

        if (byStudentsId != null) {
            byStudentsId.setStNumber(byStudentsId.getStNumber() - 1);
            groupRepository.save(byStudentsId);
        }

        student.setStatus(Status.STOPPED);
        studentRepository.save(student);

        return ResponseEntity.ok(new ResponseMessage("Student stopped successfully", null, true));

    }

    public ResponseEntity<?> debtor(Long studentId) {

        Optional<Student> byId = studentRepository.findById(studentId);

        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Student not found", null, false));
        }

        Student student = byId.get();

        Groups byStudentsId = groupRepository.findByStudentsId(student.getId());

        if (byStudentsId != null) {
            byStudentsId.setStNumber(byStudentsId.getStNumber() - 1);
            groupRepository.save(byStudentsId);
        }

        student.setStatus(Status.DEBTOR);
        studentRepository.save(student);
        return ResponseEntity.ok(new ResponseMessage("Debtor student marked successfully", null, true));


    }

    public ResponseEntity<?> removeStudentFromGroup(Long studentId, Long groupId) {

        Optional<Student> byId = studentRepository.findById(studentId);
        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Student not found", null, false));
        }

        Optional<Groups> byGroupId = groupRepository.findById(groupId);
        if (byGroupId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Group not found", null, false));
        }

        Student student = byId.get();
        Groups group = byGroupId.get();
        student.setAddedGroup(false);
        group.getStudents().remove(student);
        group.setStNumber(group.getStNumber() - 1);
        groupRepository.save(group);
        studentRepository.save(student);

        return ResponseEntity.ok(new ResponseMessage("Student removed from group successfully", null, true));

    }

    public ResponseEntity<?> getDebtors() {

        List<Student> byStatus = studentRepository.findByStatus(Status.DEBTOR);

        if (byStatus == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("No debtors found", null, false));
        }

        return ResponseEntity.ok(new ResponseMessage("Success", byStatus, true));

    }

    public ResponseEntity<?> notDebtor(Long studentId) {

        Optional<Student> byId = studentRepository.findById(studentId);

        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Student not found", null, false));
        }


        Student student = byId.get();

        if ( student.getStatus().equals(Status.DEBTOR) ){
            student.setStatus(Status.ACTIVE);
            studentRepository.save(student);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Student is already not a debtor", null, false));
        }

        return ResponseEntity.ok(new ResponseMessage("Student marked as not debtor successfully", null, true));

    }

    public ResponseEntity<?> getStoppedStudents() {

        List<Student> byStatus = studentRepository.findByStatus(Status.STOPPED);

        if (byStatus == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("No stopped students found", null, false));
        }

        return ResponseEntity.ok(new ResponseMessage("Success", byStatus, true));
    }

    public ResponseEntity<?> markNotStopped(Long studentId) {

        Optional<Student> byId = studentRepository.findById(studentId);

        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Student not found", null, false));
        }

        Student student = byId.get();

        if ( student.getStatus().equals(Status.STOPPED) ){
            student.setStatus(Status.ACTIVE);
            studentRepository.save(student);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Student is already not stopped", null, false));
        }

        return ResponseEntity.ok(new ResponseMessage("Student marked as not stopped successfully", null, true));
    }
}

