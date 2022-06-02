package SEP4Data.air4you.threshold;

import SEP4Data.air4you.threshold.Threshold;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendThresholdToGateway implements ISendThresholdToGateway
{

    private final RestTemplate restTemplate;
    ThresholdHolder thresholdHolder;

    public SendThresholdToGateway(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        thresholdHolder = ThresholdHolder.getInstance();
    }



    @Override
    public void sendThresholdToGateway() {


            Threshold threshold = thresholdHolder.getThreshold();
            if(threshold!=null){
                String url = "http://gatewayapplication-env.eba-p3capzyf.eu-west-1.elasticbeanstalk.com/send/tempThreshold/";
//                String url = "http://localhost:8080/send/tempThreshold/";

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.APPLICATION_JSON);

                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                Map<String, Object> map = new HashMap<>();
                map.put("roomId", threshold.getRoomId());
                map.put("minTemp", threshold.getMaxTemp());
                map.put("maxTemp", threshold.getMinTemp());
                map.put("minHumidity", threshold.getMaxHumidity());
                map.put("maxHumidity", threshold.getMinHumidity());

                HttpEntity<Map<String, Object>> tempThreshold = new HttpEntity<>(map, headers);

                this.restTemplate.postForLocation(url, tempThreshold);

                System.out.println("Sent temperature threshold to Gateway");
        }

//        if (response.getStatusCode() == HttpStatus.CREATED) {
//            return response.getBody();
//        } else {
//            return null;
//
//        }
    }
}
