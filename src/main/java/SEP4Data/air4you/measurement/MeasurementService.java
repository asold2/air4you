package SEP4Data.air4you.measurement;

import SEP4Data.air4you.humidityThreshold.HumidityThresholdRepository;
import SEP4Data.air4you.tempThreshold.TempThresholdRepository;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeasurementService implements IMeasurementService{

    @Autowired
    MeasurementRepository measurementRepository;

    @Autowired
    HumidityThresholdRepository humidityThresholdRepository;

    @Autowired
    TempThresholdRepository tempThresholdRepository;

    @Override
    public void addMeasurement(Measurement measurement) {

            measurementRepository.save(measurement);

            //TODO check if beyond threshold


    }

    @Override
    public List<Measurement> getMeasurements(String roomId) {
        List<Measurement> toReturn = new ArrayList<>();
        for (Measurement measurement:measurementRepository.findAll()) {
            if(measurement.getRoomId().equals(roomId)){
                toReturn.add(measurement);
            }
        }

        return toReturn;
    }

    @Override
    public void deleteAllFromRoom(String roomId) {

    }

    @Override
    public void deleteAllFromUser(String userId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Measurement getLastMeasurementByRoomId(String roomId) {
        List<Measurement> roomsMeasuremnts = new ArrayList<>();
        for (Measurement measurement: measurementRepository.findAll()) {
            if(measurement.getRoomId().equals(roomId)){
                roomsMeasuremnts.add(measurement);
            }
        }
        return roomsMeasuremnts.get(roomsMeasuremnts.size()-1);
    }
}
