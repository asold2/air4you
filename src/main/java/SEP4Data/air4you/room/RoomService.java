package SEP4Data.air4you.room;

import SEP4Data.air4you.measurement.Measurement;
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

    Room getRoomById(String roomId);

    void updateUserIdForRoom(String roomId, String userid);

//    void setLatestMeasurementForRoom(Measurement measurement);
}
