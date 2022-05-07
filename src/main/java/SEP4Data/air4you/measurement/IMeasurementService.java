package SEP4Data.air4you.measurement;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMeasurementService {
    void addMeasurement(Measurement measurement);
    List<Measurement> getMeasurements(String roomId);
    void deleteAllFromRoom(String roomId);
    void deleteAllFromUser(String userId);
    void deleteAll();

}
