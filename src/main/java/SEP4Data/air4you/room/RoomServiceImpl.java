package SEP4Data.air4you.room;

//import SEP4Data.air4you.StageJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements IRoomService
{

    @Autowired
    private RoomRepository roomRepository;



    @Override
    public boolean registerRoom(Room room) {
        room.setRegistrationDate(LocalDateTime.now());
        if(room.getRoomId()!=null){
        if(roomRepository.existsById(room.getRoomId())){
            return false;
        }
        else{
            roomRepository.save(room);
            return true;
        }
        }
        return false;

    }

    @Override
    public List<Room> getRooms(String userId) {
        ArrayList<Room> temp = new ArrayList<>();
        for(Room room : roomRepository.findAll()){
            if(room.getUserId().equals(userId)){
                temp.add(room);
            }
        }
        return temp;
    }



    @Override
    public boolean deleteRoom(String roomId) {

        if (roomRepository.existsById(roomId)){
            roomRepository.deleteById(roomId);
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public boolean updateRoom(Room room) {
        if(roomRepository.existsById(room.getRoomId())){
            roomRepository.updateRoomName(room.getName(), room.getRoomId(), room.getUserId());
            return true;
        }
        return false;
    }

    @Override
    public void updateUserIdForRoom(String roomId, String userid) {
        Room room = roomRepository.getById(roomId);
        room.setUserId(userid);
        roomRepository.save(room);
    }

    @Override
    public boolean deleteRoomsByUserId(String userId) {
        if(roomRepository.existsByUserId(userId)){
            roomRepository.deleteRoomsByUserId(userId);
            return true;
        }

        return false;
    }




}
