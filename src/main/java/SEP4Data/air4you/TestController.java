package SEP4Data.air4you;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    FirebaseOptions options;
    String property = System.getenv("spring.datasource.url");

    {
        try {
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .setDatabaseUrl("https://" + ".firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


 // s
    @GetMapping("/")
    String test(){
        return "Hi Data Warehouse group from SEP4 group 1!\n " + property;
    }
}
