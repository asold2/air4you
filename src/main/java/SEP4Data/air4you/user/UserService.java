package SEP4Data.air4you.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;




    public boolean register(String username, String password) {
        User user = new User(username, password);
        if(!userRepository.findAll().contains(user)){
            userRepository.save(new User(username, password));
            return true;
        }
        else{
            return false;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean deleteAll() {
        userRepository.deleteAll();
        return true;
    }
}
