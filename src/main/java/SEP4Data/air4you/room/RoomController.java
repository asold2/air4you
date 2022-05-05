package SEP4Data.air4you.room;

import SEP4Data.air4you.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/room/registration/")
    public int registerRoom(@RequestBody Room room){
        if(roomService.registerRoom(room)){
            return HttpServletResponse.SC_OK;
        }
        else {
            return HttpServletResponse.SC_FORBIDDEN;
        }
    }

    @GetMapping("/rooms/{userId}")
    public List<Room> getRooms(@PathVariable String userId){
        return roomService.getRooms(userId);
    }

    @GetMapping("/all/rooms/")
    public List<Room> getAllRooms(){
        System.out.println("Getting all rooms again again again!!!");
        return roomService.getAllRooms();
    }

    @PutMapping("empty/room/of/user/")
    public void deleteUserFromRoom(@RequestBody Room room){
        System.out.println("testing testing");
        roomService.deleteUserFromRoom(room);
    }

    @DeleteMapping("/abortion/")
    public void deleteAllRoomsFromUser(@RequestBody String userId){

        roomService.deleteAllFromUser(userId);
    }

    @DeleteMapping("/deletion/")
    public void deleteAllRooms(){

        roomService.deleteAll();
    }

}
