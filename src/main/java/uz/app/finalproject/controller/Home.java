package uz.app.finalproject.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.app.finalproject.dto.DashboardDTO;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.repository.GroupRepository;
import uz.app.finalproject.repository.StudentRepository;
import uz.app.finalproject.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class Home {

    final StudentRepository studentRepository;
    final GroupRepository groupRepository;
    final UserRepository staffRepository;

    public Home(StudentRepository studentRepository, GroupRepository groupRepository, UserRepository staffRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.staffRepository = staffRepository;
    }

    @GetMapping
    public ResponseEntity<?> home() {

        DashboardDTO dashboard = new DashboardDTO();

        dashboard.setActive_students(studentRepository.countByStatusNotIn(List.of(Status.ARCHIVE , Status.ACTIVELY_LEFT)));
        dashboard.setGroups(groupRepository.countByStatus(Status.ACTIVE));
        dashboard.setStaffs(staffRepository.countByStatus(Status.ACTIVE));
        dashboard.setActively_left_students(studentRepository.countByStatusNotIn(List.of(Status.ARCHIVE , Status.ARCHIVE)));


        return ResponseEntity.ok(new ResponseMessage( "Dashboard info" , dashboard , true));

    }
}
