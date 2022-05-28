package SEP4Data.air4you.Notification;

import SEP4Data.air4you.Notification.Token.UserToken;
import antlr.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationController {

    @Autowired
    private MainActivity mainActivity;

    @GetMapping("/notification/")
    public void registerRoom(){
        mainActivity.sendNotification("fkzrJnqlTxKTYBE71Suz8h:APA91bFj83Yx4KQeSSwssDIhAvhJ7hmvmK_2TZOIyj_ZIscVZB4I5Z46iMStbqLSs7IJO7Qa2903fJQ6-V5WhnFSGZnRP_5noR9-6gO4dUl9A-rMkpmq3BJaK8kxlW2u5rpRvL10dLKB" ,new Data("DataBody", "DataTitle", true));
    }

    @PostMapping("/authenticate/")
    public int authenticate(@RequestBody UserToken userToken){
        mainActivity.sendNotification(userToken.getToken() ,new Data("DataBody", "DataTitle", true));
        return 1;
    }

}

