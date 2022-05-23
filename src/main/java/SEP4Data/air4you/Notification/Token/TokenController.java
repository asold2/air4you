package SEP4Data.air4you.Notification.Token;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class TokenController{

    @Autowired
    private TokenService tokenService;

    public TokenController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @GetMapping("/all/tokens/")
    public List<UserToken> getAllTokens(){
        for (UserToken userToken:
             tokenService.getAllTokens()) {
            System.out.println(userToken.getUid());
        }
        System.out.println("Hello");
        return tokenService.getAllTokens();
    }


    @PostMapping("/token/")
    public int CreateToken(@RequestBody UserToken userToken){
        System.out.println("Token has been created!");
        System.out.println("UserToken Token: " + userToken.getToken());
        System.out.println("UserToken UID: " + userToken.getUid());
        if( tokenService.createToken(userToken)){
            tokenService.notifyUser(userToken.getToken());
            return HttpServletResponse.SC_OK;
        }
        else {
            // Changed from Forbidden (403) to Expectation_Failed (417)
            return HttpServletResponse.SC_EXPECTATION_FAILED;
        }
    }

    @PutMapping("/token/")
    public int DeleteToken(@RequestBody UserToken userToken){
       return tokenService.deleteToken(userToken);
    }

    @DeleteMapping("delete/tokens/")
    public void deleteAllTokens(){
        tokenService.deleteAll();
    }


}
