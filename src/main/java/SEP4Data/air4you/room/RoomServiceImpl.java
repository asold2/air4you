package SEP4Data.air4you.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public boolean registerRoom(int roomId) {
        if(roomRepository.existsById(roomId)){
            return  false;
        }
        else{
            roomRepository.save(new Room(roomId));
            return true;
        }
    }

    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
}
