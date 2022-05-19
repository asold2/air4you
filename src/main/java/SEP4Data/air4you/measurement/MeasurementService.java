package SEP4Data.air4you.measurement;

import SEP4Data.air4you.Notification.Data;
import SEP4Data.air4you.Notification.MainActivity;
import SEP4Data.air4you.Notification.PushNotification;
import SEP4Data.air4you.Notification.Token.TokenService;
import SEP4Data.air4you.common.Threshold;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public  Threshold addMeasurement(Measurement measurement) {

            measurementRepository.save(measurement);
        System.out.println(returnCurrentTempThreshold(measurement.getRoomId()) + "BJHVGKHCFGCHYLYV!!!!!!!!!!!!");

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

                if (isInsideThreshold(measurement.getDate(), threshold.getStartTime(), threshold.getEndTime()))
                {

                    if (measurement.getTemperature() > threshold.getMax())
                    {
                        data = new Data("Temperature is too high",
                            "Threshold has been reached", String.valueOf(measurement.getTemperature()));

                        mainActivity.sendNotification(to, data);

                    }
                    else if (measurement.getTemperature() > threshold.getMin())
                    {
                        data = new Data("Temperature is too low",
                            "Threshold has been reached", String.valueOf(measurement.getTemperature()));

                        mainActivity.sendNotification(to, data);
                        // Todo send notification too low
                    }
                }
            }
            for (HumidityThreshold threshold :
                    humidityThresholds) {
                if (isInsideThreshold(measurement.getDate(), threshold.getStartTime(), threshold.getEndTime()))
                {
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
            return returnCurrentTempThreshold(measurement.getRoomId());
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
    public  Threshold returnCurrentTempThreshold(String roomId) {
        LocalTime currentTime = LocalTime.now();
        TemperatureThreshold temperatureThreshold = null;
        for (TemperatureThreshold temp: tempThresholdService.getAllTempThresholdsByRoomId(roomId)) {
            if (temp.getStartTime().isBefore(currentTime) && temp.getEndTime().isAfter(currentTime)){
                temperatureThreshold = temp;
            }
        }

        return temperatureThreshold;
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

    private boolean isInsideThreshold(Date timestamp, LocalTime startTime, LocalTime endTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);

        int measurementHour = calendar.get(Calendar.HOUR_OF_DAY);
        int measurementMinute = calendar.get(Calendar.MINUTE);
        int measurementSecond = calendar.get(Calendar.SECOND);

        if (measurementHour > startTime.getHour() && measurementHour < endTime.getHour()){
            if (measurementMinute > startTime.getMinute() && measurementMinute < endTime.getMinute()){
                if (measurementSecond > startTime.getSecond() && measurementSecond < endTime.getSecond()){
                    System.out.println("Inside threshold");
                    return true;
                }
            }
        }

        System.out.println("Inside threshold");
        return false;
    }

}
