package uz.app.finalproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.finalproject.dto.StatusDTO;
import uz.app.finalproject.dto.UserDTO;
import uz.app.finalproject.service.StaffService;


@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {


    private final StaffService staffService;


    @PostMapping()
    public ResponseEntity<?> getStaffs(@RequestBody StatusDTO statusDTO) {
        return staffService.getStaffs(statusDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDTO staffDTO) {
        return staffService.addStaff(staffDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody UserDTO staffDTO) {
        return staffService.updateStaff(id, staffDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        return staffService.deleteStaff(id);

    }


}
