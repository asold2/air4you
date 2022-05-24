package SEP4Data.air4you.tempThreshold;
import SEP4Data.air4you.humidityThreshold.HumidityThreshold;
import SEP4Data.air4you.measurement.Measurement;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


public interface ITempThresholdService {

    List<TemperatureThreshold> getAllTempThresholds();
    boolean addTempThreshold(TemperatureThreshold temperatureThreshold);
    void deleteTempThreshold(int Id);
    void updateTempThreshold(TemperatureThreshold temperatureThreshold);
    TemperatureThreshold returnCurrentTempThreshold(String roomId, Date measurementDate);
    Measurement isInsideThreshold(Measurement measurement, TemperatureThreshold temperatureThreshold);

    List<TemperatureThreshold> getAllTempThresholdsByRoomId(String roomId);

    void deleteAll();
}
