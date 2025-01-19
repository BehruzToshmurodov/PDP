package uz.app.finalproject.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.finalproject.dto.RoomDTO;
import uz.app.finalproject.entity.Groups;
import uz.app.finalproject.entity.ResponseMessage;
import uz.app.finalproject.entity.Room;
import uz.app.finalproject.repository.GroupRepository;
import uz.app.finalproject.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    final RoomRepository repository;
    final GroupRepository groupRepository;

    public RoomService(RoomRepository repository, GroupRepository groupRepository) {
        this.repository = repository;
        this.groupRepository = groupRepository;
    }


    public ResponseEntity<?> getRooms() {
        List<Room> rooms = repository.findAll();

        if (rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("No rooms found", List.of(), false));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("All rooms", rooms, true));
    }


    public ResponseEntity<?> deleteRoom(String id) {
        try {
            Long roomId = Long.valueOf(id);

            if (!repository.existsById(roomId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Room not found", null, false));
            }

            Optional<Room> roomOptional = repository.findById(roomId);
            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();

                List<Groups> groupsUsingRoom = groupRepository.findByRoom(room);
                if (!groupsUsingRoom.isEmpty()) {
                    for (Groups group : groupsUsingRoom) {
                        group.setRoom(null);
                        groupRepository.save(group);
                    }
                }

                repository.deleteById(roomId);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("Room deleted successfully", null, true));
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Room not found", null, false));

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid room ID format", null, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("An error occurred while deleting the room", null, false));
        }
    }



    public ResponseEntity<?> updateRoom(String id, RoomDTO roomDTO) {
        try {
            Long roomId = Long.valueOf(id);

            Optional<Room> roomOptional = repository.findById(roomId);
            if (roomOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Room not found", null, false));
            }

            Room room = roomOptional.get();
            room.setRoomName(roomDTO.getRoomName());
            room.setCapacity(roomDTO.getCapacity());
            room.setCountOfTable(roomDTO.getCountOfTable());
            room.setCountOfChair(roomDTO.getCountOfChair());

            repository.save(room);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Room updated successfully", roomDTO, true));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid room ID format", null, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("An error occurred while updating the room", null, false));
        }
    }


    public ResponseEntity<?> createRoom(RoomDTO roomDTO) {
        if (roomDTO == null || roomDTO.getRoomName() == null || roomDTO.getRoomName().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Invalid room data", null, false));
        }

        try {
            Room room = new Room();
            room.setRoomName(roomDTO.getRoomName());
            room.setCapacity(roomDTO.getCapacity());
            room.setCountOfTable(roomDTO.getCountOfTable());
            room.setCountOfChair(roomDTO.getCountOfChair());

            repository.save(room);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage("Room created successfully", roomDTO, true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error creating room: " + e.getMessage(), null, false));
        }
    }


    public ResponseEntity<?> search(String search) {

        if (search == null || search.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Search query cannot be empty", null, false));
        }

        try {
            List<Room> allByRoomNameContains = repository.findAllByRoomNameContains(search);

            if (allByRoomNameContains.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("No rooms found for the given search query", null, false));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Search results", allByRoomNameContains, true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error during search: " + e.getMessage(), null, false));
        }
    }


}
