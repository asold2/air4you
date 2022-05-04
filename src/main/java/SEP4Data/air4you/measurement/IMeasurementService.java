package SEP4Data.air4you.measurement;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMeasurementService {
    void addMeasurement(Measurement measurement);
    List<Measurement> getMeasurements(int roomId);
    void deleteAllFromRoom(int roomId);
    void deleteAllFromUser(int userId);
    void deleteAll();

}
