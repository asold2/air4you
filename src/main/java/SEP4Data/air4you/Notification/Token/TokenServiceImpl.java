package SEP4Data.air4you.Notification.Token;

import SEP4Data.air4you.humidityThreshold.HumidityThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public boolean createToken(UserToken newUserToken) {
       tokenRepository.save(newUserToken);
       return true;
    }


    @Override
    public void deleteToken(UserToken oldUserToken) {
        tokenRepository.delete(oldUserToken);
    }

    @Override
    public boolean updateToken(UserToken updatedUserToken) {
       tokenRepository.save(updatedUserToken);
       return true;
    }

    @Override
    public String getToken(String uId) {
        System.out.println(uId + "!!!!!!!!@@#@#$");
        for (UserToken temp : tokenRepository.findAll())
        {
            System.out.println(temp.getuId()+ "AAAAAAADSECDCE");
            if (temp.getuId().equals(uId))
            {
               return temp.getToken();
            }

        }
        return null;

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
