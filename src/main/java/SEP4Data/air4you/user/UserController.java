package SEP4Data.air4you.user;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user/registration/{username}/{password}/")

    public int registerUser(@PathVariable String username, @PathVariable String password){
        if(userService.register(username, password)){
            return HttpServletResponse.SC_OK;
        } else{
            return HttpServletResponse.SC_FORBIDDEN;
        }
    }

    @GetMapping("/users/")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/total_deletion")
    public int deleteUsers(){
        if(userService.deleteAll()){
            return  HttpServletResponse.SC_ACCEPTED;
        }
        else{
            return  HttpServletResponse.SC_BAD_REQUEST;
        }
    }

    @DeleteMapping("/users/{userId}")
    public int deleteUser(@PathVariable String userId){
        if(userService.deleteUser(userId)){
            return HttpServletResponse.SC_OK;
        } else {
            return HttpServletResponse.SC_NOT_FOUND;
        }
    }

}


