package SEP4Data.air4you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import stage.Extract;

@SpringBootApplication
//@ComponentScan({"SEP4Data.air4you.StageJDBC"})

public class Air4youApplication {

	public static void main(String[] args) {
		SpringApplication.run(Air4youApplication.class, args);

//		Extract extract = new Extract();
//		extract.stageDimUserCreation();
//
//		kj
//	extract.stageDimHumidityThresholdCreation();

	}

}


// you have to update the application you uploaded to the beanstalk