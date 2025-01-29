package uz.app.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.finalproject.dto.RoomDTO;
import uz.app.finalproject.entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Boolean existsByRoomName(String name);

}
