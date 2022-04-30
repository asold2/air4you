package SEP4Data.air4you.room;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    boolean registerRoom(Room room);
    List<Room> getRooms(String userId);

    List<Room> getAllRooms();

    void deleteAll();

    void deleteUserFromRoom(Room room);

    void deleteAllFromUser(String userId);
}
