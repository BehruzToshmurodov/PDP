package uz.app.finalproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.finalproject.dto.FinanceDTO;
import uz.app.finalproject.service.FinanceService;

import java.time.LocalDate;

@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor

public class FinanceController {

    private final FinanceService financeService;


    @GetMapping()
    public ResponseEntity<?> finances() {
        return financeService.getFinances();
    }


    @PostMapping("/add")
    public ResponseEntity<?> addFinance(@RequestBody FinanceDTO financeDTO) {
        return financeService.createFinance(financeDTO);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateFinance(@PathVariable Long id, @RequestBody FinanceDTO financeDTO) {
        return financeService.update(id, financeDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFinance(@PathVariable Long id) {
        return financeService.delete(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterFinances(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return financeService.filter(startDate, endDate);
    }

    @GetMapping("/courseFees")
    public ResponseEntity<?> getCourseFees() {
        return financeService.getCourseFee();
    }

}
