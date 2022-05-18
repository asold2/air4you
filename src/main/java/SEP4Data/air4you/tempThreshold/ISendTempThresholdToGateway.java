package SEP4Data.air4you.tempThreshold;

import org.springframework.stereotype.Service;

@Service
public interface ISendTempThresholdToGateway {
    void sendTempThresholdToGateway(TemperatureThreshold temperatureThreshold);
}
