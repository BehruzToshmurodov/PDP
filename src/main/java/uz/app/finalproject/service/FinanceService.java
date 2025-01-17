package uz.app.finalproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import uz.app.finalproject.dto.FinanceDTO;
import uz.app.finalproject.repository.FinanceRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final FinanceRepository financeRepository;



    public ResponseEntity<?> createFinance(FinanceDTO financeDTO) {

    }

    public ResponseEntity<?> getFinances() {

    }

    public ResponseEntity<?> update(Integer id, FinanceDTO financeDTO) {


    }

    public ResponseEntity<?> delete(Integer id) {


    }

    public ResponseEntity<?> filter(LocalDate startDate, LocalDate endDate) {


    }
}
