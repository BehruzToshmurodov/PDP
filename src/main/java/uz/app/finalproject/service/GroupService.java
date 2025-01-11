package uz.app.finalproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.finalproject.dto.GroupDTO;
import uz.app.finalproject.entity.*;
import uz.app.finalproject.repository.AttendanceRepository;
import uz.app.finalproject.repository.GroupRepository;
import uz.app.finalproject.repository.RoomRepository;
import uz.app.finalproject.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    final GroupRepository groupRepository;
    final UserRepository userRepository;
    final RoomRepository roomRepository;
    final AttendanceRepository attendanceRepository;



    public ResponseEntity<?> groupsActive() {

        List<Groups> activeGroups = groupRepository.findByStatus("ACTIVE");

        if (activeGroups != null && !activeGroups.isEmpty()) {
            return ResponseEntity.ok(new ResponseMessage("All active groups", activeGroups, true));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage("No active groups found", null, false));
    }


    public ResponseEntity<?> groupsArxiv() {

        List<Groups> arxivGroups = groupRepository.findByStatus("ARCHIVE");

        if (arxivGroups != null && !arxivGroups.isEmpty()) {
            return ResponseEntity.ok(new ResponseMessage("Archived groups", arxivGroups, true));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage("No archived groups found", null, false));
    }


    public ResponseEntity<?> addGroup(GroupDTO groupDTO) {

        if (groupDTO == null || groupDTO.getGroupName() == null || groupDTO.getTeacher() == null || groupDTO.getRoom() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid group data provided", null, false));
        }

        if (groupRepository.existsByGroupNameAndStatusNot(groupDTO.getGroupName(), "ARCHIVE")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseMessage("Group with this name already exists", null, false));
        }

        User teacher = userRepository.findUserByFirstnameAndLastname(groupDTO.getTeacher().getFirstname(), groupDTO.getTeacher().getLastname());
        if (teacher == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Teacher not found", null, false));
        }

        if (!"TEACHER".equals(teacher.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseMessage("This teacher does not have the required role", null, false));
        }

        Room room = roomRepository.findByRoomName(groupDTO.getRoom());
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Room not found", null, false));
        }

        Groups groups = new Groups();
        groups.setDays(groupDTO.getDays());
        groups.setGroupName(groupDTO.getGroupName());
        groups.setStatus("ACTIVE");
        groups.setStartTime(groupDTO.getStartTime());
        groups.setTeacher(teacher);
        groups.setRoom(room);
        groupRepository.save(groups);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage("Group added successfully", groupDTO, true));
    }


    public ResponseEntity<?> searchGroup(String search) {
        if (search == null || search.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Search term cannot be empty", null, false));
        }

        List<Groups> groups = groupRepository.findAllByGroupNameContainsAndStatusEquals(search, "ACTIVE");

        if (groups.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("No active groups found for the given search term", null, false));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Search results", groups, true));
    }


    public ResponseEntity<?> updateGroup(GroupDTO groupDTO, String id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid group ID", null, false));
        }

        if (groupDTO == null || groupDTO.getGroupName() == null || groupDTO.getTeacher() == null || groupDTO.getRoom() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid group data provided", null, false));
        }

        Optional<Groups> groupOptional = groupRepository.findById(Long.valueOf(id));
        if (groupOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Group not found", null, false));
        }

        Groups group = groupOptional.get();

        User teacher = userRepository.findUserByFirstnameAndLastname(
                groupDTO.getTeacher().getFirstname(), groupDTO.getTeacher().getLastname());
        if (teacher == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Teacher not found", null, false));
        }

        Room room = roomRepository.findByRoomName(groupDTO.getRoom());
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Room not found", null, false));
        }

        group.setGroupName(groupDTO.getGroupName());
        group.setDays(groupDTO.getDays());
        group.setStatus("ACTIVE");
        group.setStartTime(groupDTO.getStartTime());
        group.setTeacher(teacher);
        group.setRoom(room);

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

        if ("ARCHIVE".equals(group.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseMessage("Group can't be deleted because it is already archived!", null, false));
        }

        group.setStatus("ARCHIVE");
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

            List<User> students = group.getStudents();

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

            List<User> students = group.getStudents();

            if (students.isEmpty()) {
                return ResponseEntity.ok(new ResponseMessage(
                        "No students found in the group", null, true));
            }

            List<Attendance> attendances = attendanceRepository.findAllByUserIn(students);

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


}
