package uz.app.finalproject.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.app.finalproject.dto.UserDTO;
import uz.app.finalproject.entity.Enums.Role;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.entity.User;
import uz.app.finalproject.repository.GroupRepository;
import uz.app.finalproject.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class StaffService {

    final UserRepository staffRepository;
    final GroupRepository groupRepository;

    public ResponseEntity<?> addStaff(UserDTO staffDTO) {

        if (staffDTO.getFirstname() == null || staffDTO.getFirstname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("First name is required", null, false));
        }
        if (staffDTO.getLastname() == null || staffDTO.getLastname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Last name is required", null, false));
        }
        if (staffDTO.getPhoneNumber() == null || staffDTO.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Phone number is required", null, false));
        }
        if (staffDTO.getPassword() == null || staffDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Password is required", null, false));
        }

        try {

            User user = new User();
            user.setFirstname(staffDTO.getFirstname());
            user.setLastname(staffDTO.getLastname());
            user.setPhoneNumber(staffDTO.getPhoneNumber());
            user.setRole(staffDTO.getRole());
            user.setGender(staffDTO.getGender());
            user.setStatus(Status.ACTIVE);
            user.setPassword(staffDTO.getPassword());

            staffRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage("Staff added successfully", staffDTO, true));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error adding staff: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> updateStaff(Long id, UserDTO staffDTO) {

        if (staffDTO.getFirstname() == null || staffDTO.getFirstname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("First name is required", null, false));
        }
        if (staffDTO.getLastname() == null || staffDTO.getLastname().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Last name is required", null, false));
        }
        if (staffDTO.getPhoneNumber() == null || staffDTO.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Phone number is required", null, false));
        }
        if (staffDTO.getPassword() == null || staffDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Password is required", null, false));
        }

        try {
            User user = staffRepository.findById(id).orElse(null);

            if (user != null && Status.ACTIVE.equals(user.getStatus())) {
                user.setFirstname(staffDTO.getFirstname());
                user.setLastname(staffDTO.getLastname());
                user.setPhoneNumber(staffDTO.getPhoneNumber());
                user.setRole(staffDTO.getRole());
                user.setGender(staffDTO.getGender());
                user.setPassword(staffDTO.getPassword());

                staffRepository.save(user);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("Staff updated successfully", staffDTO, true));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Staff not found or not active by id", id, false));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error updating staff: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> deleteStaff(Long id) {
        try {
            Optional<User> byId = staffRepository.findById(id);

            if (byId.isPresent()) {

                User staff = byId.get();

                if ( staff.getStatus().equals(Status.ARCHIVE) ){
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseMessage("Staff removed already ", id, false));
                }

                if ( staff.getStatus().equals(Status.ACTIVE) ){
                    staff.setStatus(Status.ARCHIVE);
                    staffRepository.save(staff);
                }

                List<Groups> groupsUsingStaff = groupRepository.findByTeacher(staff);

                if (!groupsUsingStaff.isEmpty()) {
                    for (Groups group : groupsUsingStaff) {
                        group.setTeacher(null);
                        groupRepository.save(group);
                    }
                }



                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("Staff removed ", id, true));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Staff not found by this id", id, false));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error archiving staff: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> getStaffs(String status) {

        switch (status) {

            case "OTHER":
                List<User> other = staffRepository.findAllByStatusAndRoleNotIn(Status.ACTIVE, List.of(Role.TEACHER, Role.STUDENT));
                if (other.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseMessage("No other staff found in active", List.of(), false));
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("All other staffs", other, true));

            case "TEACHER":
                List<User> teachers = staffRepository.findAllByRoleAndStatusNot(Role.TEACHER, Status.ARCHIVE);
                if (teachers.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseMessage("No teachers found in active", List.of(), false));
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("All teachers", teachers, true));

            case "ARCHIVE":
                List<User> archives = staffRepository.findAllByStatus( Status.ARCHIVE);
                if (archives.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseMessage("No staff found in archive", List.of(), false));
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("All archived staffs", archives, true));

            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage("Something went wrong!", null, false));
        }
    }

    public ResponseEntity<?> profile(Long id) {

        Optional<User> userOptional = staffRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            List<Groups> groups = groupRepository.findAllByTeacherAndStatus(user , Status.ACTIVE);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("User info", List.of(user , groups), true));
        }

        return ResponseEntity.ok(new ResponseMessage("Staff not found" , null , false));

    }
}
