package SEP4Data.air4you.measurement;

import SEP4Data.air4you.Notification.Data;
import SEP4Data.air4you.Notification.MainActivity;
import SEP4Data.air4you.Notification.PushNotification;
import SEP4Data.air4you.Notification.Token.TokenService;
import SEP4Data.air4you.humidityThreshold.HumidityThreshold;
import SEP4Data.air4you.humidityThreshold.HumidityThresholdRepository;
import SEP4Data.air4you.humidityThreshold.IHumidityThresholdService;
import SEP4Data.air4you.room.Room;
import SEP4Data.air4you.room.RoomService;
import SEP4Data.air4you.tempThreshold.ITempThresholdService;
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
    MainActivity mainActivity;

    @Autowired
    IHumidityThresholdService humidityThresholdService;

    @Autowired
    ITempThresholdService tempThresholdService;

    @Autowired
    TokenService tokenService;

    @Autowired
    RoomService roomService;

    @Override
    public void addMeasurement(Measurement measurement) {

            measurementRepository.save(measurement);

        //TODO check if beyond threshold

        List<HumidityThreshold> humidityThresholds = humidityThresholdService.getAllHumidityThresholdsByRoomId(measurement.getRoomId());
        List<TemperatureThreshold> temperatureThresholds = tempThresholdService.getAllTempThresholdsByRoomId(measurement.getRoomId());

        Data data;
        String to = null;

        for (Room room:
             roomService.getAllRooms()) {
            if (room.getRoomId().equals(measurement.getRoomId())){
                to = tokenService.getToken(room.getUserId());
                break;
            }
        }
//        if (!to.equals(null)) {


            for (TemperatureThreshold threshold :
                    temperatureThresholds) {
                if (measurement.getTemperature() > threshold.getMax()) {
                    data = new Data("Temperature is too high", "Threshold has been reached", String.valueOf(measurement.getTemperature()));

                    mainActivity.sendNotification(to,data);

                } else if (measurement.getTemperature() > threshold.getMin()) {
                    data = new Data("Temperature is too low", "Threshold has been reached", String.valueOf(measurement.getTemperature()));

                    mainActivity.sendNotification(to,data);
                    // Todo send notification too low
                }

            }
            for (HumidityThreshold threshold :
                    humidityThresholds) {
                if (measurement.getHumidity() > threshold.getMax()) {
                    data = new Data("Humidity is too high", "Threshold has been reached", String.valueOf(measurement.getTemperature()));

                    mainActivity.sendNotification(to,data);
                    // Todo send notification
                } else if (measurement.getHumidity() > threshold.getMin()) {
                    data = new Data("Humidity is too low", "Threshold has been reached", String.valueOf(measurement.getTemperature()));

                    mainActivity.sendNotification(to,data);
                    // Todo send notification too low
                }


            }
        }
//    }

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
