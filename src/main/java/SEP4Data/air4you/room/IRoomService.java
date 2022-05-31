package SEP4Data.air4you.room;

import SEP4Data.air4you.measurement.Measurement;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IRoomService
{
    boolean registerRoom(Room room);
    List<Room> getRooms(String userId);
    boolean deleteRoom(String roomId);
    boolean updateRoom(Room room);
    void updateUserIdForRoom(String roomId, String userid);
    boolean deleteRoomsByUserId(String userId);

}
