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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MeasurementServiceImpl implements IMeasurementService{

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

    //Adding measurement
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

        TemperatureThreshold tempThresh = tempThresholdService.returnCurrentTempThreshold(measurement.getRoomId(), measurement.getDate());
        HumidityThreshold humThresh = humidityThresholdService.returnCurrentHumidityThreshold(measurement.getRoomId(), measurement.getDate());

        if (tempThresholdService.isInsideThreshold(measurement, tempThresh).getTemperatureExceeded())
        {
            data.setBody("Temperature is outside threshold");
            data.setTitle("Threshold has been reached");
            data.setExceeded(true);

            measurement.setTemperatureExceeded(true);
            mainActivity.sendNotification(to,data);
        }

        if (humidityThresholdService.isInsideThreshold(measurement,humThresh).getHumidityExceeded())
        {
            data.setBody("Humidity is outside threshold");
            data.setTitle("Threshold has been reached");
            data.setExceeded(true);

            measurement.setHumidityExceeded(true);
            mainActivity.sendNotification(to,data);
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

    //Get measurements by room id
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

    //Delete all measurements
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

    // This method will return measurements by room id
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

    //This method will return measurements by date and room id
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

                if(inputCalendar.get(Calendar.YEAR) == measurementCalendar.get(Calendar.YEAR) &&
                        inputCalendar.get(Calendar.MONTH) == measurementCalendar.get(Calendar.MONTH) &&
                        inputCalendar.get(Calendar.DAY_OF_MONTH) == measurementCalendar.get(Calendar.DAY_OF_MONTH)){
                    newMeasurements.add(measurement);
                }
            }
        } catch (ParseException e) {
            return null;
        }
        return newMeasurements;
    }

    //This method will return measurements between two dates
    @Override
    public List<Measurement> getMeasurementsBetweenDates(String startDate, String endDate, String roomId)
    {
        List<Measurement> measurementsInRoom = roomService.getRoomById(roomId).getMeasurements();
        List<Measurement> measurementsToReturn = new ArrayList<>();


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate dateStart = LocalDate.parse(startDate, formatter);
            LocalDate dateEnd = LocalDate.parse(endDate, formatter);


            for (Measurement measurement:
                measurementsInRoom) {

                LocalDate measurementLocalDate = measurement.getDate().toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate();

                if(measurementLocalDate.isBefore(dateEnd) && measurementLocalDate.isAfter(dateStart))
                {
                    measurementsToReturn.add(measurement);
                }


            }
        return measurementsToReturn;
    }

    //This method will return measurements for week by user id
    @Override
    public List<Measurement> getMeasurementByUserAndRoomIdWeek(String userId)
    {
        List<Room> listOfRooms = roomService.getRooms(userId);
        List<Measurement> listOfMeasurements = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minus(1, ChronoUnit.WEEKS);
        List<Measurement> measurementsToReturn = new ArrayList<>();


        for(int i=0;i<listOfRooms.size();i++)
        {
            listOfMeasurements.addAll(listOfRooms.get(i).getMeasurements());
        }

        for (Measurement measurement:
            listOfMeasurements)
        {

            LocalDate measurementLocalDate = measurement.getDate().toInstant().atZone(
                ZoneId.systemDefault()).toLocalDate();

            if(measurementLocalDate.isBefore(today) && measurementLocalDate.isAfter(oneWeekAgo))
            {
                measurementsToReturn.add(measurement);
            }
        }


        return measurementsToReturn;
    }
}