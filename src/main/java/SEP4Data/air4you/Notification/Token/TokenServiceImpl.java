package SEP4Data.air4you.Notification.Token;

import SEP4Data.air4you.Notification.Data;
import SEP4Data.air4you.Notification.MainActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MainActivity mainActivity;

    @Override
    public boolean createToken(UserToken newUserToken) {
        if(tokenRepository.findUserTokenByUid(newUserToken.getUid()).isEmpty()){
            tokenRepository.save(newUserToken);
            return true;
        }
        tokenRepository.updateUserToken(newUserToken.getToken(), newUserToken.getUid());
        return true;
    }


    @Override
    public int deleteToken(UserToken oldUserToken) {
        tokenRepository.delete(oldUserToken);
        return HttpServletResponse.SC_OK;
    }

    @Override
    public boolean updateToken(UserToken updatedUserToken) {
       tokenRepository.updateUserToken(updatedUserToken.getToken(), updatedUserToken.getUid());
       return true;
    }

    @Override
    public void notifyUser(String token) {
        mainActivity.sendNotification(token,new Data("You have been given a new token","New Token!",true));
    }

    @Override
    public String getToken(String uId) {
        System.out.println(uId + "!!!!!!!!@@#@#$");
        for (UserToken temp : tokenRepository.findAll())
        {
            System.out.println(temp.getUid()+ "AAAAAAADSECDCE");
            if (temp.getUid().equals(uId))
            {
               return temp.getToken();
            }
        }
        return "";
    }

    @Override
    public void deleteAll() {
        tokenRepository.deleteAll();
    }

    @Override
    public List<UserToken> getAllTokens() {
        return tokenRepository.findAll();
    }



}
