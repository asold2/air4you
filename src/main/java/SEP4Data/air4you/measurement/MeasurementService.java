package SEP4Data.air4you.measurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService implements IMeasurementService{

    @Autowired
    MeasurementRepository measurementRepository;

    @Override
    public void addMeasurement(Measurement measurement) {
        if(measurementRepository.existsById(measurement.getTimestamp().hashCode())){

        } else {

        }
    }

    @Override
    public List<Measurement> getMeasurements(int roomId) {
        return null;
    }

    @Override
    public void deleteAllFromRoom(int roomId) {

    }

    @Override
    public void deleteAllFromUser(int userId) {

    }

    @Override
    public void deleteAll() {

    }
}
