package SEP4Data.air4you.room;

import SEP4Data.air4you.measurement.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public boolean registerRoom(Room room) {
        room.setRegistrationDate(LocalDateTime.now());
        if(roomRepository.existsById(room.getRoomId())){
            return false;
        }
        else{
            roomRepository.save(room);
            return true;
        }
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
    public void updateUserIdForRoom(String roomId, String userid) {
        Room room = roomRepository.getById(roomId);
        room.setUserId(userid);
        roomRepository.save(room);
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
