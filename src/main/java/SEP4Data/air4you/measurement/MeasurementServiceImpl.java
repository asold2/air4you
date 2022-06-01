package SEP4Data.air4you.measurement;

import SEP4Data.air4you.Notification.Data;
import SEP4Data.air4you.Notification.MainActivity;
import SEP4Data.air4you.Notification.Token.ITokenService;
import SEP4Data.air4you.humidityThreshold.HumidityThreshold;
import SEP4Data.air4you.humidityThreshold.IHumidityThresholdService;
import SEP4Data.air4you.room.Room;
import SEP4Data.air4you.room.RoomRepository;
import SEP4Data.air4you.room.IRoomService;
import SEP4Data.air4you.tempThreshold.ITempThresholdService;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import SEP4Data.air4you.threshold.Threshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class MeasurementServiceImpl implements IMeasurementService{

    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    RoomRepository roomRepository;

    //This is initiated like this because of testing
    @Autowired
    MainActivity mainActivity;

    @Autowired IRoomService IRoomService;
//

    //Adding measurement
    @Override
    public Threshold addMeasurement(Measurement measurement) {

        //TODO check if beyond threshold
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, -2);
        Date twoHoursback = cal.getTime();


        measurement.setDate(twoHoursback);

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

    @Override
    public List<Measurement> getMeasurementByUserAndRoomIdWeek(String userId) {
        List<Room> listOfRooms = IRoomService.getRooms(userId);
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
