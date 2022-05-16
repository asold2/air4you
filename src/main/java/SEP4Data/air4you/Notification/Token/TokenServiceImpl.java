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
        if(tokenRepository.existsById(newUserToken.getId())){
            return updateToken(newUserToken);
        }
        else{
            tokenRepository.save(newUserToken);
            return true;
        }
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

        for (UserToken temp : tokenRepository.findAll())
        {
            if (temp.getuId().equals(uId))
            {
               return temp.getToken();
            }

        }
        return null;

    }


}
