package SEP4Data.air4you;

import SEP4Data.air4you.Notification.MainActivity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

// This is random text I will remember to remove

public class Air4youApplication {

	static MainActivity mainActivity;

	public static void main(String[] args) {
		SpringApplication.run(Air4youApplication.class, args);

		//mainActivity.sendNotification("Test title", "Test content");

	}

}


// you have to update the application you uploaded to the beanstalk