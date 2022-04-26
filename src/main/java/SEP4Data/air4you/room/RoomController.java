package SEP4Data.air4you.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/room/registration/{roomId}/")
    public int registerRoom(@PathVariable int roomId){
        if(roomService.registerRoom(roomId)){
            return HttpServletResponse.SC_OK;
        }
        else {
            return HttpServletResponse.SC_FORBIDDEN;
        }
    }
    @GetMapping("/rooms/")
    public List<Room> getRooms(){
        return roomService.getRooms();
    }
}
