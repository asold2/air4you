package SEP4Data.air4you.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;
  //Creating new room
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

    @PutMapping("/room/")
    public int changeRoom(@RequestBody Room room){
        if(roomService.updateRoom(room)){
            return HttpServletResponse.SC_OK;
        } else {
            return HttpServletResponse.SC_NOT_FOUND;
        }
    }

  //Todo delete
    //Get room by userId
    @GetMapping("/rooms/{userId}")
    public List<Room> getRooms(@PathVariable String userId){
        return roomService.getRooms(userId);
    }

    //Gets rooms with last measurement. Body is userId as String
    //returns array list of rooms
    @GetMapping("/room/last/{userId}")
    public List<Room> getRoomsAndLastMeasurement(@PathVariable String userId){
        List<Room> roomsToReturn = new ArrayList<>();
        for (Room room: roomService.getRooms(userId)) {
            room.onlyLastMeasurement();
            roomsToReturn.add(room);
        }
        return roomsToReturn;
    }

  //Todo delete
    //Returns array list of all possible rooms
    @GetMapping("/all/rooms/")
    public List<Room> getAllRooms(){

        return roomService.getAllRooms();
    }

    @DeleteMapping("/user/{userId}")
    public int deleteRoomsByUserId(@PathVariable String userId){

        if(roomService.deleteRoomsByUserId(userId)){
            return HttpServletResponse.SC_OK;
        } else {
            return HttpServletResponse.SC_NOT_FOUND;
        }

    }

  //Todo delete
    //Delete user from room
    @PutMapping("empty/room/of/user/")
    public void deleteUserFromRoom(@RequestBody Room room){
        roomService.deleteUserFromRoom(room);
    }

  //Todo delete
    //For this method send a simple integer as user's Id in the body of http request
    @DeleteMapping("/abortion/")
    public void deleteAllRoomsFromUser(@RequestBody String userId){
        roomService.deleteAllFromUser(userId);
    }

    //This Method will remove room by roomId and all measurements it contains
    @DeleteMapping("/room/{roomId}/")
    public int deleteRoom(@PathVariable String roomId){
        System.out.println(roomId + "userId to delete user from db");
        if (roomService.deleteRoom(roomId)){
            return HttpServletResponse.SC_OK;
        }else
            return HttpServletResponse.SC_NOT_FOUND;

    }

  //Todo delete
    // This method will delete all rom if the link is called
    @DeleteMapping("/deletion/")
    public void deleteAllRooms(){

        roomService.deleteAll();
    }


    // This method will change user id for a room if the link is called it take room id and new user id
    @PostMapping("/change/user/{roomId}/{userId}")
    public void changeUserIdForRoom(@PathVariable String roomId, @PathVariable String userId){
        roomService.updateUserIdForRoom(roomId ,userId);
    }

}
