package SEP4Data.air4you.tempThreshold;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ITempThresholdService {

    List<TemperatureThreshold> getAllTempThresholds();
    boolean addTempThreshold(TemperatureThreshold temperatureThreshold);
    void deleteTempThreshold(int Id);
    void updateTempThreshold(TemperatureThreshold temperatureThreshold);


    List<TemperatureThreshold> getAllTempThresholdsByRoomId(String roomId);

    void deleteAll();
}
