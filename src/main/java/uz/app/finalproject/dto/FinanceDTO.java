package uz.app.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FinanceDTO {


    private String name;
    private LocalDate date;
    private String category;
    private String receiver;
    private Double amount;
}
