package SEP4Data.air4you;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
 // s
    @GetMapping("/")
    String test(){
        return "Hi Data Warehouse group from SEP4 group 1!";
    }
}
