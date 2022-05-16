package SEP4Data.air4you.Notification.Token;


import org.springframework.stereotype.Service;

public interface TokenService {

    boolean createToken(UserToken newUserToken);
    void deleteToken(UserToken oldUserToken);
    boolean updateToken(UserToken updatedUserToken);

}
