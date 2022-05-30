package SEP4Data.air4you.room;

//import SEP4Data.air4you.StageJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl implements RoomService{

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
    public List<Room> getAllRooms() {

        return roomRepository.findAll();
    }

    @Override
    public void deleteAll() {
        roomRepository.deleteAll();
    }

    @Override
    public void deleteUserFromRoom(Room room) {
        roomRepository.deleteById(String.valueOf(room.getRoomId()));
        room.setUserId("none");
        roomRepository.save(room);
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
    public void deleteAllFromUser(String userId) {
        for (Room room : roomRepository.findAll()){
            if (room.getUserId().equals(userId)){
                roomRepository.deleteById(String.valueOf(room.getRoomId()));
            }
        }
    }

    @Override
    public Room getRoomById(String roomId) {
        return roomRepository.getById(roomId);
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

//    @Override
//    public void setLatestMeasurementForRoom(Measurement measurement) {
//////        Room room = roomRepository.getById(measurement.getRoomId());
//////        room.setLastMeasurement(measurement);
//////        roomRepository.save(room);
////        roomRepository.updateRoomMeasurement(measurement, measurement.getRoomId());
//
//        //        room.setLastMeasuremnt(measurement);
////        roomRepository.deleteById(measurement.getRoomId());
////        roomRepository.save(room);
////        System.out.println(room.toString());
//    }




}
