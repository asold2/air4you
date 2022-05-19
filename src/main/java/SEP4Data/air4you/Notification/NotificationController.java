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
        mainActivity.sendNotification("eFv5k7HsQWucVQ0cs7E7Qh:APA91bFIcI0YG6qIcIesNdzbZPA_jnmu_pctycm_hG-QkgegVm3CeQr0KNSc1gYD3oEcqXmv3r5EZcA4z_QTbgnhnkal2b-eN5z9PdI88K6OPg21D0Hw9y6aXA_aqgHsfMum76isg09D" ,new Data("DataBody", "DataTitle", "Key1"));
        System.out.println("Skibadaba da");
    }

    @PostMapping("/authenticate/")
    public int authenticate(@RequestBody UserToken userToken){
        mainActivity.sendNotification(userToken.getToken() ,new Data("DataBody", "DataTitle", "exceeded"));
        return 1;
    }

}

