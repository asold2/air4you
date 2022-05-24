package SEP4Data.air4you.threshold;

import SEP4Data.air4you.threshold.Threshold;
import org.springframework.stereotype.Service;

@Service
public interface ISendThresholdToGateway
{
    void sendThresholdToGateway(Threshold threshold);
}
