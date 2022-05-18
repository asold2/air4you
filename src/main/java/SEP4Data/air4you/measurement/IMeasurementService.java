package SEP4Data.air4you.measurement;

import SEP4Data.air4you.common.Threshold;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMeasurementService {
    Threshold addMeasurement(Measurement measurement);
    List<Measurement> getMeasurements(String roomId);
    void deleteAllFromRoom(String roomId);
    void deleteAllFromUser(String userId);
    void deleteAll();
    Threshold returnCurrentTempThreshold(String roomId);

    Measurement getLastMeasurementByRoomId(String roomId);
}
