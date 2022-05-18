package SEP4Data.air4you.tempThreshold;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ITempThresholdService {
    List<TemperatureThreshold> getAllTempThresholds();
    boolean addTempThreshold(TemperatureThreshold temperatureThreshold);
    void deleteTempThreshold(String roomId, int thresholdId);
    void updateTempThreshold(int thresholdId, double max, double min);


    List<TemperatureThreshold> getAllTempThresholdsByRoomId(String roomId);

    void deleteAll();
}
