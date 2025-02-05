package uz.app.finalproject.controller;


import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class Home {

    final StudentRepository studentRepository;
    final GroupRepository groupRepository;
    final UserRepository staffRepository;

    @GetMapping
    public ResponseEntity<?> home() {

        DashboardDTO dashboard = new DashboardDTO();

        dashboard.setActive_students(studentRepository.countByStatus(Status.ACTIVE));
        dashboard.setGroups(groupRepository.countByStatus(Status.ACTIVE));
        dashboard.setStaffs(staffRepository.countByStatus(Status.ACTIVE));
        dashboard.setArxiv(studentRepository.countByStatus(Status.ARCHIVE));
        dashboard.setDebtors(studentRepository.countByStatus(Status.DEBTOR));
        dashboard.setStopped_students(studentRepository.countByStatus(Status.STOPPED));
        dashboard.setActively_left_students(studentRepository.countByStatus(Status.ACTIVELY_LEFT));

        return ResponseEntity.ok(new ResponseMessage( "Dashboard info" , dashboard , true));

    }
}
