package SEP4Data.air4you;

import SEP4Data.air4you.Notification.MainActivity;
import SEP4Data.air4you.room.RoomService;
import SEP4Data.air4you.room.RoomServiceImpl;
import SEP4Data.air4you.threshold.ISendThresholdToGateway;
import SEP4Data.air4you.threshold.SendThresholdToGateway;
import SEP4Data.air4you.threshold.Threshold;
import SEP4Data.air4you.threshold.ThresholdHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan
@ComponentScan({"SEP4Data.air4you.StageJDBC"})
@EnableScheduling
public class Air4youApplication {

	static MainActivity mainActivity;
	@Autowired
	static ISendThresholdToGateway iSendThresholdToGateway = new SendThresholdToGateway(new RestTemplateBuilder());


	public static void main(String[] args) {
		SpringApplication.run(Air4youApplication.class, args);
		sender();

	}
//	@Scheduled(fixedRate = 240000)
	@Scheduled(fixedRate = 240000)
	public static void sender(){
		System.out.println("SENT TO THE FUCKER 1");
		iSendThresholdToGateway.sendThresholdToGateway();
		System.out.println("SENT TO THE FUCKER");
	}
}