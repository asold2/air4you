package SEP4Data.air4you.room;

import SEP4Data.air4you.measurement.Measurement;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoomService {
    boolean registerRoom(Room room);
    List<Room> getRooms(String userId);

    List<Room> getAllRooms();

    void deleteAll();

    void deleteUserFromRoom(Room room);

    boolean deleteRoom(String roomId);
    void deleteAllFromUser(String userId);

    Room getRoomById(String roomId);

    boolean updateRoom(Room room);

    void updateUserIdForRoom(String roomId, String userid);

    boolean deleteRoomsByUserId(String userId);


//    void setLatestMeasurementForRoom(Measurement measurement);
}
