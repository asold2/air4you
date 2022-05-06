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
        if(measurementRepository.existsById(measurement.getId())){
            return;
        } else {
            measurementRepository.save(measurement);
        }
    }

    @Override
    public List<Measurement> getMeasurements(int roomId) {
        return measurementRepository.findAll();
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
