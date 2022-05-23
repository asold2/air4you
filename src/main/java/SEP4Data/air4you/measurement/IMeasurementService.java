package SEP4Data.air4you.measurement;

import SEP4Data.air4you.humidityThreshold.HumidityThreshold;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import SEP4Data.air4you.threshold.Threshold;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IMeasurementService {

    Threshold addMeasurement(Measurement measurement);
    void newAddMeasurement(Measurement measurement);
    List<Measurement> getMeasurements(String roomId);
    void deleteAllFromRoom(String roomId);
    void deleteAllFromUser(String userId);
    void deleteAll();
    TemperatureThreshold returnCurrentTempThreshold(String roomId, Date measurementDate);
    HumidityThreshold returnCurrentHumidityThreshold(String roomId, Date measurementDate);

    Measurement getLastMeasurementByRoomId(String roomId);
}
