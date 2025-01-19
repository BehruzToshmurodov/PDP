package uz.app.finalproject.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import uz.app.finalproject.dto.GroupDTO;
import uz.app.finalproject.entity.*;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    final GroupRepository groupRepository;
    final UserRepository userRepository;
    final RoomRepository roomRepository;
    final AttendanceRepository attendanceRepository;
    final StudentRepository studentRepository;


//    public ResponseEntity<?> groupsActive() {
//
//        List<Groups> activeGroups = groupRepository.findAllByStatus(Status.ACTIVE);
//
//        if (activeGroups != null && !activeGroups.isEmpty()) {
//            return ResponseEntity.ok(new ResponseMessage("All active groups", activeGroups, true));
//        }
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new ResponseMessage("No active groups found", null, false));
//    }
//
//
//    public ResponseEntity<?> groupsArxiv() {
//
//        List<Groups> arxivGroups = groupRepository.findAllByStatus(Status.ARCHIVE);
//
//        if (arxivGroups != null && !arxivGroups.isEmpty()) {
//            return ResponseEntity.ok(new ResponseMessage("Archived groups", arxivGroups, true));
//        }
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new ResponseMessage("No archived groups found", null, false));
//    }


    public ResponseEntity<?> addGroup(GroupDTO groupDTO) {

        if (  groupDTO == null || groupDTO.getCourseName() == null || groupDTO.getGroupName() == null || groupDTO.getTeacherId() == null || groupDTO.getRoomId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid group data provided", null, false));
        }

        if (groupRepository.existsByGroupNameAndStatusNot(groupDTO.getGroupName(), Status.ARCHIVE)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseMessage("Group with this name already exists", null, false));
        }

        Optional<User> teacherOptional = userRepository.findById(Long.valueOf(groupDTO.getTeacherId()));
        if (teacherOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Teacher not found", null, false));
        }

        User teacher = teacherOptional.get();

        if (!"TEACHER".equals(String.valueOf(teacher.getRole()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseMessage("This teacher does not have the required role", null, false));
        }

        Optional<Room> room = roomRepository.findById(Long.valueOf(groupDTO.getRoomId()));
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Room not found", null, false));
        }

        Room room1 = room.get();

        saveGroup(groupDTO , teacher , room1) ;


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage("Group added successfully", groupDTO, true));
    }

    private void saveGroup(GroupDTO groupDTO, User teacher, Room room) {
        Groups groups = new Groups();
        groups.setDays(groupDTO.getDays());
        groups.setGroupName(groupDTO.getGroupName());
        groups.setStatus(Status.ACTIVE);
        groups.setStartTime(groupDTO.getStartTime());
        groups.setTeacher(teacher);
        groups.setStartDate(groupDTO.getStartDate());
        groups.setEndDate(groupDTO.getEndDate());
        groups.setCourseName(groupDTO.getCourseName());
        groups.setGroupPrice(groupDTO.getGroupPrice());
        groups.setStNumber(0);
        groups.setRoom(room);
        groupRepository.save(groups);
    }


    public ResponseEntity<?> searchGroup(String search) {
        if (search == null || search.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Search term cannot be empty", null, false));
        }

        List<Groups> groups = groupRepository.findAllByGroupNameContainsAndStatusEquals(search, Status.ACTIVE);

        if (groups.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("No active groups found for the given search term", null, false));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Search results", groups, true));
    }


    public ResponseEntity<?> updateGroup(GroupDTO groupDTO, String id) {

        System.out.println("update starting !");
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid group ID", null, false));
        }

        if (groupDTO == null || groupDTO.getGroupName() == null || groupDTO.getTeacherId() == null || groupDTO.getRoomId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid group data provided", null, false));
        }

        Optional<Groups> groupOptional = groupRepository.findById(Long.valueOf(id));
        if (groupOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Group not found", null, false));
        }

        Groups group = groupOptional.get();

        Optional<User> teacher = userRepository.findById(Long.valueOf(groupDTO.getTeacherId()));
        if (teacher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Teacher not found", null, false));
        }

        Optional<Room> room = roomRepository.findById(Long.valueOf(groupDTO.getRoomId()));
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Room not found", null, false));
        }

        group.setGroupName(groupDTO.getGroupName());
        System.out.println("phone number changed");
        group.setDays(groupDTO.getDays());
        System.out.println("days changed");
        group.setStatus(Status.ACTIVE);
        group.setStartTime(groupDTO.getStartTime());
        System.out.println("start time changed");
        group.setTeacher(teacher.get());
        System.out.println("teacher changed");
        group.setRoom(room.get());
        System.out.println("room changed");

        groupRepository.save(group);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Group updated successfully", groupDTO, true));
    }


    public ResponseEntity<?> deleteGroup(@PathVariable String id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid group ID", null, false));
        }

        Optional<Groups> groupOptional = groupRepository.findById(Long.valueOf(id));

        if (groupOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Group not found", null, false));
        }

        Groups group = groupOptional.get();

        if (Status.ARCHIVE.equals(group.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseMessage("Group can't be deleted because it is already archived!", null, false));
        }

        group.setStatus(Status.ARCHIVE);

        for (Student student : group.getStudents()) {
            student.setStatus(Status.ARCHIVE);
            studentRepository.save(student);
        }

        groupRepository.save(group);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Group archived successfully", null, true));
    }




    public ResponseEntity<?> showStudentByGroupId(String id) {
        try {
            Optional<Groups> byId = groupRepository.findById(Long.valueOf(id));

            if (byId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Group not found", null, false));
            }

            Groups group = byId.get();

            List<Student> students = group.getStudents();

            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("No students in the group", students, true));
            }

            return ResponseEntity.ok(new ResponseMessage("Students in the group", students, true));

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid group ID format", null, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error retrieving students: " + e.getMessage(), null, false));
        }
    }



    public ResponseEntity<?> getAttendanceByGroupStudents(@PathVariable Long groupId) {
        try {

            Optional<Groups> groupOptional = groupRepository.findById(groupId);
            if (groupOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseMessage(
                        "Group not found", null, false));
            }

            Groups group = groupOptional.get();

            List<Student> students = group.getStudents();

            if (students.isEmpty()) {
                return ResponseEntity.ok(new ResponseMessage(
                        "No students found in the group", null, true));
            }

            List<Attendance> attendances = attendanceRepository.findAllByStudentIn(students);

            if (attendances.isEmpty()) {
                return ResponseEntity.ok(new ResponseMessage(
                        "No attendance records found for the students", null, true));
            }

            return ResponseEntity.ok(new ResponseMessage(
                    "Attendance records retrieved successfully", attendances, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseMessage(
                    "Error retrieving attendance records: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> addNewReaderToGroup(Long studentId, Long groupId) {

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                   .body(new ResponseMessage("Student not found", null, false));
        }

        Student student = studentOptional.get();

        Optional<Groups> groupsOptional = groupRepository.findById(groupId);

        if (groupsOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                   .body(new ResponseMessage("Group not found", null, false));
        }

        Groups group = groupsOptional.get();

        student.setAddedGroup(true);
        group.setStNumber(group.getStNumber()+1);
        group.getStudents().add(student);

        studentRepository.save(student);
        groupRepository.save(group);


        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Student added to group successfully" , student , true));
    }

    public ResponseEntity<?> getStudentWithoutGroup() {

        List<Student> students = studentRepository.findAllByAddedGroupAndStatus(false , Status.ACTIVE);

        if (students.isEmpty()) {
            return ResponseEntity.ok(new ResponseMessage("No students without a group", students, true));
        }

        return ResponseEntity.ok(new ResponseMessage("Students without a group", students, true));

    }

    public ResponseEntity<?> getGroups(String status) {
        if ("ACTIVE".equals(status)) {
            return ResponseEntity.ok(new ResponseMessage("Active groups", groupRepository.findAllByStatus(Status.ACTIVE), true));
        } else if ("ARCHIVE".equals(status)) {
            return ResponseEntity.ok(new ResponseMessage("Archived groups", groupRepository.findAllByStatus(Status.ARCHIVE), true));
        } else {
            return ResponseEntity.ok(new ResponseMessage("Invalid status", null, false));
        }

}

    public ResponseEntity<?> profile(Long groupId) {

        Optional<Groups> byId = groupRepository.findById(groupId);

        if ( byId.isEmpty()){
            return ResponseEntity.ok(new ResponseMessage("Group not found" , null , false));
        }

        Groups group = byId.get();

       return ResponseEntity.ok(new ResponseMessage("Group information" , group  , true));

    }
}
