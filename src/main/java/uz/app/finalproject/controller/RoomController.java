package uz.app.finalproject.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.finalproject.dto.RoomDTO;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.entity.Room;
import uz.app.finalproject.repository.RoomRepository;
import uz.app.finalproject.service.RoomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<?> getRooms() {
        return roomService.getRooms();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable String id) {
        return roomService.deleteRoom(id);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateRoom(@PathVariable String id, RoomDTO roomDTO) {
        return roomService.updateRoom(id, roomDTO);
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody RoomDTO roomDTO) {
        return roomService.createRoom(roomDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String search) {
        return roomService.search(search);
    }

}
