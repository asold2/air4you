package SEP4Data.air4you;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    String test(){
        return "Hi, my name is, what? My name is, who?\n" +
                "My name is, chka-chka, Slim Shady";
    }

}
