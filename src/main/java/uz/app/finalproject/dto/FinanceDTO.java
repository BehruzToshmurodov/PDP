package uz.app.finalproject.dto;


import java.time.LocalDate;

public class FinanceDTO {


    private String name;
    private LocalDate date;
    private String category;
    private String receiver;
    private Double amount;


    public FinanceDTO(String name, LocalDate date, String category, String receiver, Double amount) {
        this.name = name;
        this.date = date;
        this.category = category;
        this.receiver = receiver;
        this.amount = amount;
    }

    public FinanceDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
