package SEP4Data.air4you.threshold;

import SEP4Data.air4you.tempThreshold.ISendTempThresholdToGateway;
import org.springframework.stereotype.Service;

@Service
public class ThresholdService implements ISendTempThresholdToGateway {


    @Override
    public void sendTempThresholdToGateway(Threshold temperatureThreshold) {

    }
}
