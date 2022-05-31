package SEP4Data.air4you.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private IRoomService IRoomService;
  //Creating new room
    @PostMapping("/room/")
    public int registerRoom(@RequestBody Room room){
        if(IRoomService.registerRoom(room)){
            return HttpServletResponse.SC_OK;
        }
        else {
            // Changed from Forbidden (403) to Expectation_Failed (417)
            return HttpServletResponse.SC_EXPECTATION_FAILED;
        }
    }

    @PutMapping("/room/")
    public int changeRoom(@RequestBody Room room){
        if(IRoomService.updateRoom(room)){
            return HttpServletResponse.SC_OK;
        } else {
            return HttpServletResponse.SC_NOT_FOUND;
        }
    }

  //Todo delete
    //Get room by userId
    @GetMapping("/rooms/{userId}")
    public List<Room> getRooms(@PathVariable String userId){
        return IRoomService.getRooms(userId);
    }

    //Gets rooms with last measurement. Body is userId as String
    //returns array list of rooms
    @GetMapping("/room/last/{userId}")
    public List<Room> getRoomsAndLastMeasurement(@PathVariable String userId){
        List<Room> roomsToReturn = new ArrayList<>();
        for (Room room: IRoomService.getRooms(userId)) {
            room.onlyLastMeasurement();
            roomsToReturn.add(room);
        }
        return roomsToReturn;
    }


    @DeleteMapping("/user/{userId}")
    public int deleteRoomsByUserId(@PathVariable String userId){

        if(IRoomService.deleteRoomsByUserId(userId)){
            return HttpServletResponse.SC_OK;
        } else {
            return HttpServletResponse.SC_NOT_FOUND;
        }

    }

    //This Method will remove room by roomId and all measurements it contains
    @DeleteMapping("/room/{roomId}/")
    public int deleteRoom(@PathVariable String roomId){
        System.out.println(roomId + "userId to delete user from db");
        if (IRoomService.deleteRoom(roomId)){
            return HttpServletResponse.SC_OK;
        }else
            return HttpServletResponse.SC_NOT_FOUND;

    }



    // This method will change user id for a room if the link is called it take room id and new user id
    @PostMapping("/change/user/{roomId}/{userId}")
    public void changeUserIdForRoom(@PathVariable String roomId, @PathVariable String userId){
        IRoomService.updateUserIdForRoom(roomId ,userId);
    }

}
