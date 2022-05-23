package SEP4Data.air4you.measurement;

import SEP4Data.air4you.Notification.Data;
import SEP4Data.air4you.Notification.MainActivity;
import SEP4Data.air4you.Notification.Token.TokenService;
import SEP4Data.air4you.humidityThreshold.HumidityThreshold;
import SEP4Data.air4you.humidityThreshold.IHumidityThresholdService;
import SEP4Data.air4you.room.Room;
import SEP4Data.air4you.room.RoomService;
import SEP4Data.air4you.tempThreshold.ITempThresholdService;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import SEP4Data.air4you.threshold.Threshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public Threshold addMeasurement(Measurement measurement) {



        //TODO check if beyond threshold
        Date date = measurement.getDate();
        measurement.setDate(date);

        Data data = new Data();
        data.setExceeded(false);
        String to = null;

        for (Room room:
             roomService.getAllRooms()) {
            if (room.getRoomId().equals(measurement.getRoomId())){
                to = tokenService.getToken(room.getUserId());
                break;
            }
        }

        System.out.println("Measurement date = " + measurement.getDate());

        TemperatureThreshold tempThresh = returnCurrentTempThreshold(measurement.getRoomId(), measurement.getDate());
        HumidityThreshold humThresh = returnCurrentHumidityThreshold(measurement.getRoomId(), measurement.getDate());

        if (isInsideThreshold(measurement.getDate(), tempThresh.getStartTime(), tempThresh.getEndTime()))
        {
            if (measurement.getTemperature() > tempThresh.getMax())
            {
                data.setBody("Temperature is too high");
                data.setTitle("Threshold has been reached");
                data.setExceeded(true);

                measurement.setTemperatureExceeded(true);
                mainActivity.sendNotification(to, data);
            }
            else if (measurement.getTemperature() < tempThresh.getMin())
            {
                data.setExceeded(true);
                measurement.setTemperatureExceeded(true);
                mainActivity.sendNotification(to, data);
            }
        }

        if (isInsideThreshold(measurement.getDate(), humThresh.getStartTime(), humThresh.getEndTime()))
        {
            if (measurement.getHumidity() > humThresh.getMax()) {
                data.setBody("Humidity is too high");
                data.setTitle("Threshold has been reached");
                data.setExceeded(true);

                measurement.setHumidityExceeded(true);
                mainActivity.sendNotification(to,data);
                // Todo send notification
            } else if (measurement.getHumidity() < humThresh.getMin()) {
                data.setBody("Humidity is too low");
                data.setTitle("Threshold has been reached");
                data.setExceeded(true);

                measurement.setHumidityExceeded(true);
                mainActivity.sendNotification(to,data);
            }
        }

        if(measurement.getCo2() > 600){
            measurement.setCo2Exceeded(true);
            data.setBody("Co2 is too high");
            data.setTitle("Threshold has been reached");
            data.setExceeded(true);
            mainActivity.sendNotification(to,data);
        }

        mainActivity.sendNotification(to,data);

        measurementRepository.save(measurement);

        Threshold thresholdToReturn = new Threshold(measurement.getRoomId(), tempThresh.getMin(), tempThresh.getMax(), humThresh.getMin(), humThresh.getMax());

        return thresholdToReturn;
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
        measurementRepository.deleteAll();
    }

    @Override
    public  TemperatureThreshold returnCurrentTempThreshold(String roomId, Date measurementDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(measurementDate);

        // Change Measurement Date type into LocalTime dataType.
        LocalTime measurementTime = LocalTime.of(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));

        // Check with measurement LocalTime which threshold is valid
        TemperatureThreshold temperatureThreshold = null;
        for (TemperatureThreshold temp: tempThresholdService.getAllTempThresholdsByRoomId(roomId)) {
            if (temp.getStartTime().isBefore(measurementTime) && temp.getEndTime().isAfter(measurementTime)){
                temperatureThreshold = temp;
            }
        }

        // If no thresholds are valid
        if(temperatureThreshold == null){
            temperatureThreshold = new TemperatureThreshold(0,0);
        }
        return temperatureThreshold;
    }

    @Override
    public HumidityThreshold returnCurrentHumidityThreshold(String roomId, Date measurementDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(measurementDate);

        // Change Measurement Date type into LocalTime dataType.
        LocalTime measurementTime = LocalTime.of(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));

        HumidityThreshold humidityThreshold = null;
        for (HumidityThreshold temp: humidityThresholdService.getAllHumidityThresholdsByRoomId(roomId)) {
            if (temp.getStartTime().isBefore(measurementTime) && temp.getEndTime().isAfter(measurementTime)){
                humidityThreshold = temp;
            }
        }
        if(humidityThreshold == null){
            humidityThreshold = new HumidityThreshold(0,0);
        }
        return humidityThreshold;
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

    @Override
    public List<Measurement> getMeasurementByDateAndRoomId(String dateInString, String roomId) {
        List<Measurement> measurementsInRoom = roomService.getRoomById(roomId).getMeasurements();
        List<Measurement> newMeasurements = new ArrayList<>();

        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateInString);

            Calendar inputCalendar = Calendar.getInstance();
            inputCalendar.setTime(date);

            for (Measurement measurement:
                    measurementsInRoom) {
                Calendar measurementCalendar = Calendar.getInstance();
                measurementCalendar.setTime(measurement.getDate());
                if(inputCalendar.YEAR == measurementCalendar.YEAR &&
                        inputCalendar.MONTH == measurementCalendar.MONTH &&
                        inputCalendar.DATE == measurementCalendar.DATE){
                    newMeasurements.add(measurement);
                }
            }
        } catch (ParseException e) {
            return null;
        }
        return newMeasurements;
    }

    private boolean isInsideThreshold(Date timestamp, LocalTime startTime, LocalTime endTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);

        int measurementHour = calendar.get(Calendar.HOUR_OF_DAY);
        int measurementMinute = calendar.get(Calendar.MINUTE);

        if(startTime != null && endTime != null) {

            System.out.println(measurementHour + ":" + measurementMinute + "AAAAAAAAAAABBBBBBBBBCCCCCCCC" + startTime.getHour() + ":" + startTime.getMinute());

            if (measurementHour >= startTime.getHour() && measurementHour <= endTime.getHour()) {
                return true;
            } else if (measurementHour == startTime.getHour() || measurementHour == endTime.getHour()) {
                if (measurementMinute > startTime.getMinute() && measurementMinute < endTime.getMinute()) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

}
