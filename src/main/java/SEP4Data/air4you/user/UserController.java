package SEP4Data.air4you.user;

import SEP4Data.air4you.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    }
    else{
        return HttpServletResponse.SC_FORBIDDEN;
    }
}

@GetMapping("/users/")
    public List<User> getUsers(){
        return userService.getAllUsers();
}
}
