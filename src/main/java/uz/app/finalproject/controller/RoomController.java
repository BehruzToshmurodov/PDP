package uz.app.finalproject.controller;


import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor

public class RoomController {

    private final RoomService roomService;


    @GetMapping
    public ResponseEntity<?> getRooms() {
        return roomService.getRooms();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
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

    @GetMapping("/{id}")
    public ResponseEntity<?> search(@PathVariable Long id) {
        return roomService.search(id);
    }

}
