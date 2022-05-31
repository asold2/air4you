package SEP4Data.air4you.Notification.Token;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class TokenController{

    @Autowired
    private ITokenService ITokenService;

    public TokenController(ITokenService ITokenService){
        this.ITokenService = ITokenService;
    }

    //Todo delete
    @GetMapping("/all/tokens/")
    public List<UserToken> getAllTokens(){
        for (UserToken userToken:
             ITokenService.getAllTokens()) {
            System.out.println(userToken.getUid());
        }
        return ITokenService.getAllTokens();
    }


    @PostMapping("/token/")
    public int createToken(@RequestBody UserToken userToken){

        if( ITokenService.createToken(userToken)){
            ITokenService.notifyUser(userToken.getToken());
            return HttpServletResponse.SC_OK;
        }
        else {
            // Changed from Forbidden (403) to Expectation_Failed (417)
            return HttpServletResponse.SC_EXPECTATION_FAILED;
        }
    }

    @PutMapping("/token/")
    public int deleteToken(@RequestBody UserToken userToken){
       return ITokenService.deleteToken(userToken);
    }



}
