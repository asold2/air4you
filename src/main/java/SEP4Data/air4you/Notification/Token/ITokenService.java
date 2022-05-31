package SEP4Data.air4you.Notification.Token;


import org.springframework.stereotype.Service;

import java.util.List;
public interface ITokenService
{

    boolean createToken(UserToken newUserToken);
    int deleteToken(UserToken oldUserToken);
    void notifyUser(String token);
    List<UserToken> getAllTokens();
}
