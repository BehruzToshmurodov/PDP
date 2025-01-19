package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FinanceRepository extends JpaRepository<Finance, Long> {

    List<Finance> findAllByDateBetween(LocalDate startDate , LocalDate endDate);

}
