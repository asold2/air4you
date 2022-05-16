package SEP4Data.air4you.Notification.Token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class TokenController{

    @Autowired
    TokenService tokenService;


    @PostMapping("/token/")
    public int CreateToken(@RequestBody UserToken userToken){
        tokenService.createToken(userToken);
        if( tokenService.createToken(userToken)){
            return HttpServletResponse.SC_OK;
        }
        else {
            // Changed from Forbidden (403) to Expectation_Failed (417)
            return HttpServletResponse.SC_EXPECTATION_FAILED;
        }
    }

    @DeleteMapping("/token/")
    public void DeleteToken(@RequestBody UserToken userToken){
        tokenService.deleteToken(userToken);
    }


}
