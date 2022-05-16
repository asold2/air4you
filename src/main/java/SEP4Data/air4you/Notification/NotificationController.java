package SEP4Data.air4you.Notification;

import antlr.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationController {

    @Autowired
    private MainActivity mainActivity;

    @GetMapping("/notification/")
    public void registerRoom(){
        mainActivity.sendNotification( new Data("DataBody", "DataTitle", "Key1"));
        System.out.println("Skibadaba da");
    }

    @PutMapping("/authenticate/")
    public int authenticate(RequestBody UserId){
        return 1;
    }

}

