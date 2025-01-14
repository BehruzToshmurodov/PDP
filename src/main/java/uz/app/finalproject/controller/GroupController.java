package uz.app.finalproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.finalproject.dto.GroupDTO;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.entity.Room;
import uz.app.finalproject.entity.User;
import uz.app.finalproject.repository.GroupRepository;
import uz.app.finalproject.repository.RoomRepository;
import uz.app.finalproject.repository.UserRepository;
import uz.app.finalproject.service.GroupService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

//    @GetMapping("/active")
//    public ResponseEntity<?> groupsActive() {
//        return groupService.groupsActive();
//    }
//
//    @GetMapping("/notActive")
//    public ResponseEntity<?> groupsArxiv() {
//        return groupService.groupsArxiv();
//    }


    @GetMapping("/{status}")
    public ResponseEntity<?> getGroupByStatus(@PathVariable String status){
       return groupService.getGroups(status);
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
    public ResponseEntity<?> updateGroup(@RequestBody GroupDTO groupDTO, @PathVariable String id) {
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
    public ResponseEntity<?> attendance(@PathVariable Long groupId){
        return groupService.getAttendanceByGroupStudents(groupId);
    }


    @GetMapping("/getStudentWithoutGroup")
    public ResponseEntity<?> getStudentWithoutGroup() {
        return groupService.getStudentWithoutGroup();
    }

    @PostMapping("/addNewReader/{studentId}/{groupId}")
    public ResponseEntity<?> addNewReader(@PathVariable Long studentId , @PathVariable Long groupId) {
       return groupService.addNewReaderToGroup(studentId , groupId);
    }




}
