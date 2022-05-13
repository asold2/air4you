package SEP4Data.air4you.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private MainActivity mainActivity;

    @GetMapping("/notification/")
    public void registerRoom(){
        mainActivity.sendNotification( new Data("DataBody", "DataTitle", "Key1"));
        System.out.println("Skibadaba da");
    }
}
