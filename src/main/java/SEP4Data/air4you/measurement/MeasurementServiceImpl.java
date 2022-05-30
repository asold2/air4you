package SEP4Data.air4you.measurement;

import SEP4Data.air4you.Notification.Data;
import SEP4Data.air4you.Notification.MainActivity;
import SEP4Data.air4you.Notification.Token.TokenService;
import SEP4Data.air4you.humidityThreshold.HumidityThreshold;
import SEP4Data.air4you.humidityThreshold.IHumidityThresholdService;
import SEP4Data.air4you.room.Room;
import SEP4Data.air4you.room.RoomRepository;
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
import java.util.*;

@Service
public class MeasurementServiceImpl implements IMeasurementService{

    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    RoomRepository roomRepository;

    MainActivity mainActivity = new MainActivity();
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
        String to = measurementRepository.getTokenFromRoomId(measurement.getRoomId());

        LocalDateTime ldt = LocalDateTime.ofInstant(measurement.getDate().toInstant(),ZoneId.systemDefault());
        LocalTime localTime = ldt.toLocalTime();
        System.out.println(localTime);

        HumidityThreshold humThresh;
        TemperatureThreshold tempThresh;

        humThresh = measurementRepository.getCurrentHumidityThreshold(localTime, measurement.getRoomId());
        tempThresh = measurementRepository.getCurrentTemperatureThreshold(localTime, measurement.getRoomId());

        if(humThresh == null){
            humThresh = new HumidityThreshold();
        }
        if(tempThresh == null){
            tempThresh = new TemperatureThreshold();
        }

        data.setTitle(measurementRepository.getRoomName(measurement.getRoomId()));

        if(tempThresh != null && (measurement.getTemperature() < tempThresh.getMin() || measurement.getTemperature() > tempThresh.getMax())){
            measurement.setTemperatureExceeded(true);
        }

        if(tempThresh != null && (measurement.getHumidity() < humThresh.getMin() || measurement.getHumidity() > humThresh.getMax())){
            measurement.setTemperatureExceeded(true);
        }

        if(measurement.getCo2() > 600){
            measurement.setCo2Exceeded(true);
        }

        data.setBody(createNotification(measurement).getBody());
        data.setExceeded(createNotification(measurement).isExceeded());

        mainActivity.sendNotification(to,data);

        measurementRepository.save(measurement);

        Threshold thresholdToReturn = new Threshold(measurement.getRoomId(), (int)tempThresh.getMin(), (int)tempThresh.getMax(), (int)humThresh.getMin(), (int)humThresh.getMax());


        if(thresholdToReturn.getMaxHumidity()==0 && thresholdToReturn.getMinHumidity()==0 && thresholdToReturn.getMaxTemp()==0 && thresholdToReturn.getMinHumidity()==0){
            System.out.println("returning null instead of threshold");
            return null;
        }

        return thresholdToReturn;
    }

    @Override
    public Data createNotification(Measurement measurement){
        Data data = new Data();
        if(measurement.getTemperatureExceeded() || measurement.getHumidityExceeded() || measurement.getCo2Exceeded()){

            data.setExceeded(true);

            String body = "Exceeded values for: ";

            if(measurement.getTemperatureExceeded()){
                body += "Temperature";
            }
            if(measurement.getHumidityExceeded()) {
                if(measurement.getTemperatureExceeded()){
                    if(measurement.getCo2Exceeded()) {
                        body += ",";
                    } else {
                        body += " and";
                    }
                }
                body += " Humidity";

            }
            if(measurement.getCo2Exceeded()){

                if(measurement.getTemperatureExceeded() || measurement.getHumidityExceeded()){
                    body += " and";
                }

                body += " CO2";
            }
            data.setBody(body);
        }
        return data;
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
    public boolean deleteAllFromRoom(String roomId) {
        if(roomRepository.existsById(roomId)) {
            measurementRepository.deleteMeasurementsByRoomId(roomId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteAllFromUser(String userId) {

    }

    //Delete all measurements
    @Override
    public void deleteAll() {
        measurementRepository.deleteAll();
    }


    // This method will return measurements by room id
    @Override
    public Measurement getLastMeasurementByRoomId(String roomId) {
        List<Measurement> roomsMeasurements = new ArrayList<>();
        for (Measurement measurement: measurementRepository.findAll()) {
            if(measurement.getRoomId().equals(roomId)){
                roomsMeasurements.add(measurement);
            }
        }
        return roomsMeasurements.get(roomsMeasurements.size()-1);
    }

    @Override
    public List<Double> getAverageTemp(String roomId) {
        ArrayList<Double> temperatures = new ArrayList<Double>();
        for (int i = 0; i < 7; i++) {
            temperatures.add(measurementRepository.countAverageTemperature(roomId, i));
        }
        return temperatures;
    }


    @Override
    public List<Double> getAverageHumidity(String roomId) {
        ArrayList<Double> humidities = new ArrayList<Double>();
        for (int i = 0; i < 7; i++) {
            humidities.add(measurementRepository.countAverageHumidity(roomId, i));
        }
        return humidities;
    }

    @Override
    public List<Double> getAverageCo2(String roomId) {
        ArrayList<Double> temperatures = new ArrayList<Double>();
        for (int i = 0; i < 7; i++) {
            temperatures.add(measurementRepository.countAverageCo2(roomId, i));
        }
        return temperatures;
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
