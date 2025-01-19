package uz.app.finalproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;
    private Integer capacity;
    private Integer countOfTable;
    private Integer countOfChair;


    public Room(Long id, String roomName, Integer capacity, Integer countOfTable, Integer countOfChair) {
        this.id = id;
        this.roomName = roomName;
        this.capacity = capacity;
        this.countOfTable = countOfTable;
        this.countOfChair = countOfChair;
    }

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCountOfTable() {
        return countOfTable;
    }

    public void setCountOfTable(Integer countOfTable) {
        this.countOfTable = countOfTable;
    }

    public Integer getCountOfChair() {
        return countOfChair;
    }

    public void setCountOfChair(Integer countOfChair) {
        this.countOfChair = countOfChair;
    }
}
