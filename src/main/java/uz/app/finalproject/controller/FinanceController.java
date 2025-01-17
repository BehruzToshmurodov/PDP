package uz.app.finalproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.finalproject.dto.FinanceDTO;
import uz.app.finalproject.service.FinanceService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/finance")
public class FinanceController  {

    private final FinanceService financeService;



    @PostMapping("/add")
    public ResponseEntity<?> addFinance(FinanceDTO financeDTO) {
        return financeService.createFinance(financeDTO);

    }


    @GetMapping
    public ResponseEntity<?> getAllFinance() {
        return financeService.getFinances();

    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> editFinance(@PathVariable Integer id, @RequestBody FinanceDTO financeDTO) {
        return financeService.update(id , financeDTO);
    }


    @DeleteMapping("/delete{id}")
    public ResponseEntity<?> deleteFinance(@PathVariable Integer id) {
        return financeService.delete(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody LocalDate startDate, @RequestBody LocalDate endDate) {
        return financeService.filter(startDate , endDate);
    }
}
