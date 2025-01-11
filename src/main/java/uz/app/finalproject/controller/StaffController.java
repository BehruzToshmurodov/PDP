package uz.app.finalproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.finalproject.dto.UserDTO;
import uz.app.finalproject.dto.getStaffDTO;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.service.StaffService;


@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {


    private final StaffService staffService;


    @PostMapping()
    public ResponseEntity<?> getStaffs(@RequestBody getStaffDTO getStaffDTO){
       return staffService.getStaff(getStaffDTO.getStatus());
    }


//    @GetMapping("/teacher")
//    public ResponseEntity<?> getStaffs() {
//        return staffService.getStaffs();
//    }
//
//    @GetMapping("/arxiv")
//    public ResponseEntity<?> staffArxiv() {
//        return staffService.staffArxiv();
//    }
//
//    @GetMapping("/other")
//    public ResponseEntity<?> staffOther() {
//        return staffService.staffOther();
//    }

    @PostMapping("/create")
    public ResponseEntity<?> addStaff(@RequestBody UserDTO staffDTO) {
        return staffService.addStaff(staffDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody UserDTO staffDTO) {
        return staffService.updateStaff(id, staffDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        return staffService.deleteStaff(id);

    }


}
