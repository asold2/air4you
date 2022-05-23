package SEP4Data.air4you.tempThreshold;

import SEP4Data.air4you.threshold.Threshold;
import org.springframework.stereotype.Service;

@Service
public interface ISendTempThresholdToGateway {
    void sendTempThresholdToGateway(Threshold temperatureThreshold);
}
