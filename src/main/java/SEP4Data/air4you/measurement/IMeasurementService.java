package SEP4Data.air4you.measurement;

import SEP4Data.air4you.Notification.Data;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import SEP4Data.air4you.threshold.Threshold;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IMeasurementService {
    void addMeasurement(Measurement measurement);
    Data createNotification(Measurement measurement);
    List<Measurement> getMeasurements(String roomId);
    boolean deleteAllFromRoom(String roomId);
    List<Double> getAverageTemp(String roomId);
    List<Double> getAverageHumidity(String roomId);
    List<Double> getAverageCo2(String roomId);

    List<Measurement> getMeasurementByUserAndRoomIdWeek(String userId);

}
