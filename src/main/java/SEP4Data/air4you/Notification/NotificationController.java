package SEP4Data.air4you.Notification;

import SEP4Data.air4you.Notification.Token.UserToken;
import antlr.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationController {

    @Autowired
    private MainActivity mainActivity;


    @PostMapping("/authenticate/")
    public int authenticate(@RequestBody UserToken userToken){
        mainActivity.sendNotification(userToken.getToken() ,new Data("DataBody", "DataTitle", true));
        return 1;
    }

}

