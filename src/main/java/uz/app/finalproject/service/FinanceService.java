package uz.app.finalproject.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.app.finalproject.dto.CourseFeeDTO;
import uz.app.finalproject.dto.FinanceDTO;
import uz.app.finalproject.entity.Enums.Status;
import uz.app.finalproject.entity.Finance;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.repository.FinanceRepository;
import uz.app.finalproject.repository.GroupRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class FinanceService {

     final FinanceRepository financeRepository;
     final GroupRepository groupRepository;

    public FinanceService(FinanceRepository financeRepository, GroupRepository groupRepository) {
        this.financeRepository = financeRepository;
        this.groupRepository = groupRepository;
    }


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
        double allAmount = 0.0;
        for (Finance finance : finances) {
           allAmount = allAmount + finance.getAmount();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("All finances" , List.of(finances , allAmount) , true));
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


        List<Finance> allFinances = financeRepository.findAll();

        if (allFinances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("No finances found", null, false));
        }

        List<Finance> finances = allFinances.stream()
                .filter(finance -> finance.getDate() != null)
                .filter(finance -> !finance.getDate().isBefore(startDate))
                .filter(finance -> !finance.getDate().isAfter(endDate))
                .toList();

        if (finances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("No finances found within the specified date range", null, false));
        }

        return ResponseEntity.ok().body(new ResponseMessage("Finances", finances, true));
    }

    public ResponseEntity<?> getCourseFee() {
        List<CourseFeeDTO> courseFeeList = new ArrayList<>();
        double totalPrice = 0.0;

        for (Groups groups : groupRepository.findAllByStatus(Status.ACTIVE)) {
            courseFeeList.add(new CourseFeeDTO(groups.getCourseName(), groups.getGroupPrice(), groups.getStartDate()));
            totalPrice += groups.getGroupPrice();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("Course fees" , courseFeeList);
        data.put("total price" , totalPrice);


        return ResponseEntity.ok().body( new ResponseMessage("Course fee" , data , true) );
    }

    public ResponseEntity<?> filterForCourseFee(LocalDate startDate, LocalDate endDate) {
        List<CourseFeeDTO> courseFeeList = new ArrayList<>();
        double totalPrice = 0.0;

        for (Groups groups : groupRepository.findAllByStatus(Status.ACTIVE)) {
            if (groups.getStartDate().isAfter(startDate) && groups.getStartDate().isBefore(endDate)) {
                courseFeeList.add(new CourseFeeDTO(groups.getCourseName(), groups.getGroupPrice(), groups.getStartDate()));
                totalPrice += groups.getGroupPrice();
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("Course fees" , courseFeeList);
        data.put("total price" , totalPrice);


        return ResponseEntity.ok().body( new ResponseMessage("Course fee" , data , true) );
    }

}
