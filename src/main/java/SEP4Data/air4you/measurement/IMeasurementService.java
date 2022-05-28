package SEP4Data.air4you.measurement;

import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import SEP4Data.air4you.threshold.Threshold;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IMeasurementService {
    Threshold addMeasurement(Measurement measurement);
    List<Measurement> getMeasurements(String roomId);
    boolean deleteAllFromRoom(String roomId);
    void deleteAllFromUser(String userId);
    void deleteAll();
    TemperatureThreshold returnCurrentTempThreshold(String roomId, Date measurementDate);

    Measurement getLastMeasurementByRoomId(String roomId);

    List<Double> getAverageTemp(String roomId);
    List<Double> getAverageHumidity(String roomId);
    List<Double> getAverageCo2(String roomId);


    List<Measurement> getMeasurementByDateAndRoomId(String date, String roomId);
    List<Measurement> getMeasurementsBetweenDates(String startDate, String endDate, String roomId);
    List<Measurement> getMeasurementByUserAndRoomIdWeek(String userId);
}
