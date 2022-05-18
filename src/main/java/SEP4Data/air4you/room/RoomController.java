package SEP4Data.air4you.room;

import SEP4Data.air4you.measurement.IMeasurementService;
import SEP4Data.air4you.measurement.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private IMeasurementService measurementService;



    @PostMapping("/room/")
    public int registerRoom(@RequestBody Room room){
        if(roomService.registerRoom(room)){
            return HttpServletResponse.SC_OK;
        }
        else {
            // Changed from Forbidden (403) to Expectation_Failed (417)
            return HttpServletResponse.SC_EXPECTATION_FAILED;
        }
    }

    @GetMapping("/rooms/{userId}")
    public List<Room> getRooms(@PathVariable String userId){
        return roomService.getRooms(userId);
    }

    //Gets rooms with last measurement. Body is userId as String
    //returns array list of rooms
    @PostMapping("/room/last/{userId}")
    public List<Room> getRoomsLastMeasurment(@PathVariable String userId){
        System.out.println(userId + "!!!!!!!!!!!!!!!");
        List<Room> roomsToReturn = new ArrayList<>();
        for (Room room: roomService.getRooms(userId)) {
            room.onlyLastMeasurement();
            roomsToReturn.add(room);
        }
        return roomsToReturn;
    }
    //Returns array list of all possible rooms
    @PostMapping("/all/rooms/")
    public List<Room> getAllRooms(){
        System.out.println("Getting all rooms again again again again once more and again!!!");

        return roomService.getAllRooms();
    }

    @PutMapping("empty/room/of/user/")
    public void deleteUserFromRoom(@RequestBody Room room){
        System.out.println("testing testing");
        roomService.deleteUserFromRoom(room);
    }

    //For this method send a simple integer as user's Id in the body of http request
    @DeleteMapping("/abortion/")
    public void deleteAllRoomsFromUser(@RequestBody String userId){
        System.out.println(userId + "userId to delete user from db");
        roomService.deleteAllFromUser(userId);
    }

    @DeleteMapping("/deletion/")
    public void deleteAllRooms(){

        roomService.deleteAll();
    }

    @PostMapping("/change/user/{roomId}/{userId}")
    public void changeUserIdForRoom(@PathVariable String roomId, @PathVariable String userId){
        roomService.updateUserIdForRoom(roomId ,userId);
    }

}
