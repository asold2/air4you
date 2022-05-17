package SEP4Data.air4you.Notification.Token;


import org.springframework.stereotype.Service;

import java.util.List;

public interface TokenService {

    boolean createToken(UserToken newUserToken);
    void deleteToken(UserToken oldUserToken);
    boolean updateToken(UserToken updatedUserToken);
    void notifyUser(String token);
    String getToken(String uId);

    void deleteAll();

    List<UserToken> getAllTokens();
}
