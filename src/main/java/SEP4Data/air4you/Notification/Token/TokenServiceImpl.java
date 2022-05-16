package SEP4Data.air4you.Notification.Token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    TokenRepository tokenRepository;

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


}
