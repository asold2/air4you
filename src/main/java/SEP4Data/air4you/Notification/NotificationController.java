package SEP4Data.air4you.Notification;

import SEP4Data.air4you.room.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class NotificationController {

    @Autowired
    private MainActivity mainActivity;

    @GetMapping("/notification/")
    public void registerRoom(){
        mainActivity.sendNotification("Title", "Content");
        System.out.println("Skibadaba da");
    }

}
