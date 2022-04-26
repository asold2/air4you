package SEP4Data.air4you.room;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    boolean registerRoom(int roomId);

    List<Room> getRooms();
}
