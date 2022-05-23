package SEP4Data.air4you.tempThreshold;

import SEP4Data.air4you.threshold.Threshold;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendThresholdToGateway implements  ISendTempThresholdToGateway {

    private final RestTemplate restTemplate;

    public SendThresholdToGateway(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public void sendTempThresholdToGateway(Threshold temperatureThreshold) {
        String url = "http://localhost:8080/send/tempThreshold/";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("roomId", temperatureThreshold.getRoomId());
        map.put("minTemp", temperatureThreshold.getMaxTemp());
        map.put("maxTemp", temperatureThreshold.getMinTemp());
        map.put("minHumidity", temperatureThreshold.getMaxHumidity());
        map.put("maxHumidity", temperatureThreshold.getMinHumidity());

        HttpEntity<Map<String, Object>> tempThreshold = new HttpEntity<>(map, headers);

        ResponseEntity<Threshold> response = this.restTemplate.postForEntity(url, tempThreshold, Threshold.class);

        System.out.println("Sent temperature threshold to Gateway");

//        if (response.getStatusCode() == HttpStatus.CREATED) {
//            return response.getBody();
//        } else {
//            return null;
//
//        }
    }
}
