package uz.app.finalproject.dto;

public class RoomDTO {

    private String roomName;
    private Integer capacity;
    private Integer countOfTable;
    private Integer countOfChair;


    public RoomDTO(String roomName, Integer capacity, Integer countOfTable, Integer countOfChair) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.countOfTable = countOfTable;
        this.countOfChair = countOfChair;
    }

    public RoomDTO() {
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
