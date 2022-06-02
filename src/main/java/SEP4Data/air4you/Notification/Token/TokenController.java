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
    // This method will return all tokens if this link is called

    @GetMapping("/all/tokens/")
    public List<UserToken> getAllTokens(){
        for (UserToken userToken:
             ITokenService.getAllTokens()) {
            System.out.println(userToken.getUid());
        }
        return ITokenService.getAllTokens();
    }

     // You can create new token by calling this link
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
    // You can delete token by calling this link
    @PutMapping("/token/")
    public int deleteToken(@RequestBody UserToken userToken){
       return ITokenService.deleteToken(userToken);
    }



}
