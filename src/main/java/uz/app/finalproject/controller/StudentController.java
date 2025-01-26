package uz.app.finalproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.finalproject.dto.StudentDTO;
import uz.app.finalproject.dto.UserDTO;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.entity.Student;
import uz.app.finalproject.entity.User;
import uz.app.finalproject.repository.GroupRepository;
import uz.app.finalproject.repository.UserRepository;
import uz.app.finalproject.service.StudentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.addStudent(studentDTO);
    }


    @GetMapping
    public ResponseEntity<?> getStudent() {
        return studentService.getStudents();
    }


    @GetMapping("/arxiv")
    public ResponseEntity<?> getArxiv() {
        return studentService.getArxiv();
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable Long id) {
        return studentService.updateStudent(studentDTO, id);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }


    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String search) {
        return studentService.search(search);
    }


    @PostMapping("/attendance/{studentId}/{groupId}")
    public ResponseEntity<?> attendance(@PathVariable Long studentId , @PathVariable Long groupId) {
        return studentService.attendance(studentId , groupId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> search(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }

}