package uz.app.finalproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.app.finalproject.dto.FinanceDTO;
import uz.app.finalproject.entity.Finance;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.repository.FinanceRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinanceService {

     final FinanceRepository financeRepository;


    public ResponseEntity<?> createFinance( FinanceDTO financeDTO) {
        try {

            Finance fInance = new Finance();
            fInance.setName(financeDTO.getName());
            fInance.setAmount(financeDTO.getAmount());
            fInance.setDate(financeDTO.getDate());
            fInance.setCategory(financeDTO.getCategory());
            fInance.setReceiver(financeDTO.getReceiver());

            financeRepository.save(fInance);

            ResponseMessage response = new ResponseMessage("Finance successfully added", financeDTO, true);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ResponseMessage errorResponse = new ResponseMessage("Failed to add finance", null, false);
            return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
        }
    }


    public ResponseEntity<?> getFinances() {
        List<Finance> finances = financeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("All finances" , finances , true));
    }

    public ResponseEntity<?> update(Long id, FinanceDTO financeDTO) {

        Optional<Finance> byId = financeRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Finance not found", null, false));
        }

        Finance finance = byId.get();
        finance.setName(financeDTO.getName());
        finance.setAmount(financeDTO.getAmount());
        finance.setDate(financeDTO.getDate());
        finance.setCategory(financeDTO.getCategory());
        finance.setReceiver(financeDTO.getReceiver());

        financeRepository.save(finance);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Finance updated successfully", financeDTO, true));

    }

    public ResponseEntity<?> delete(Long id) {
        financeRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Finance deleted successfully", null, true));
    }

    public ResponseEntity<?> filter(LocalDate startDate, LocalDate endDate) {

        List<Finance> finances = financeRepository.findAllByDateBetween(startDate, endDate);

        return ResponseEntity.ok().body(new ResponseMessage("Finances" , finances , true));
    }
}
