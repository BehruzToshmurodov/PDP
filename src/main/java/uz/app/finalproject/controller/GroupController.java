package uz.app.finalproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.finalproject.dto.GroupDTO;

import uz.app.finalproject.service.GroupService;

import java.util.List;


@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;


    @GetMapping("/{status}")
    public ResponseEntity<?> getGroupByStatus(@PathVariable String status) {
        return groupService.getGroups(status);
    }

    @GetMapping("/profile/{groupId}")
    public ResponseEntity<?> profile(@PathVariable Long groupId) {
        return groupService.profile(groupId);
    }


    @PostMapping("/addGroup")
    public ResponseEntity<?> addGroup(@RequestBody GroupDTO groupDTO) {
        return groupService.addGroup(groupDTO);
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchGroup(@RequestParam String search) {
        return groupService.searchGroup(search);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateGroup(@RequestBody GroupDTO groupDTO, @PathVariable Long id) {
        return groupService.updateGroup(groupDTO, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable String id) {
        return groupService.deleteGroup(id);
    }


    @GetMapping("/studentsInGroup/{id}")
    public ResponseEntity<?> showStudentByGroupId(@PathVariable String id) {
        return groupService.showStudentByGroupId(id);
    }


    @GetMapping("/attendance/{groupId}")
    public ResponseEntity<?> attendance(@PathVariable Long groupId) {
        return groupService.getAttendanceByGroupStudents(groupId);
    }


    @GetMapping("/getStudentWithoutGroup")
    public ResponseEntity<?> getStudentWithoutGroup() {
        return groupService.getStudentWithoutGroup();
    }

    @PostMapping("/addNewReader/{groupId}")
    public ResponseEntity<?> addNewReader(@RequestBody List<Long> studentIds, @PathVariable Long groupId) {
        return groupService.addNewReaderToGroup(studentIds , groupId);
    }


    @PostMapping("/groupAttendance/{groupId}")
    public ResponseEntity<?> groupAttendance(@PathVariable Long groupId){
        return groupService.attendanceGroup(groupId);
    }


    @GetMapping("groups_and_attendances/{teacherId}")
    public ResponseEntity<?> groupAndAttendance(@PathVariable Long teacherId){
        return groupService.getGroupsAndAttendancesByTeacherId(teacherId);
    }


    @GetMapping("get_group_by_id/{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable Long groupId){
       return groupService.getById(groupId);
    }

    @PostMapping("/remove/{studentId}/{groupId}")
    public ResponseEntity<?> removeStudent(@PathVariable Long studentId, @PathVariable Long groupId){
        return groupService.removeStudentFromGroup(studentId, groupId);
    }

}
