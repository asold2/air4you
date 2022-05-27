package SEP4Data.air4you;

import SEP4Data.air4you.Notification.MainActivity;
import SEP4Data.air4you.room.RoomService;
import SEP4Data.air4you.room.RoomServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import stage.Extract;

@SpringBootApplication
@ComponentScan
@ComponentScan({"SEP4Data.air4you.StageJDBC"})

public class Air4youApplication {

	static MainActivity mainActivity;

	public static void main(String[] args) {
		SpringApplication.run(Air4youApplication.class, args);

		Extract extract = new Extract();
//		extract.stageDimUserCreation();
//
//		kj
//	extract.stageDimHumidityThresholdCreation();
		//mainActivity.sendNotification("Test title", "Test content");

	}

}


// you have to update the application you uploaded to the beanstalk