package uz.app.finalproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.finalproject.dto.UserDTO;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.entity.User;
import uz.app.finalproject.repository.GroupRepository;
import uz.app.finalproject.repository.UserRepository;
import uz.app.finalproject.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @PostMapping("/add{groupId}")
    public ResponseEntity<?> addStudent(@RequestBody UserDTO userDto, @PathVariable String groupId) {
        return studentService.addStudent(userDto, groupId);
    }


    @GetMapping
    public ResponseEntity<?> getStudent() {
        return studentService.getStudent();
    }


    @GetMapping("/arxiv")
    public ResponseEntity<?> getArxiv() {
        return studentService.getArxiv();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@RequestBody UserDTO userDto, @PathVariable Integer id) {
        return studentService.updateStudent(userDto, id);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        return studentService.deleteStudent(id);
    }


    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String search) {
        return studentService.search(search);
    }


    @PostMapping("/attendance/{id}")
    public ResponseEntity<?> attendance(@PathVariable Long id){
       return studentService.attendance(id);
    }




}