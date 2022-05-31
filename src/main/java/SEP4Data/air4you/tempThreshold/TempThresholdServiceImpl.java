package SEP4Data.air4you.tempThreshold;

import SEP4Data.air4you.humidityThreshold.HumidityThreshold;
import SEP4Data.air4you.measurement.Measurement;
import SEP4Data.air4you.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class TempThresholdServiceImpl implements ITempThresholdService{

    @Autowired
    private TempThresholdRepository tempThresholdRepository;
    @Autowired
    private RoomRepository roomRepository;

//    @Autowired
//    private ISendTempThresholdToGateway sendTempThresholdToGateway;


    // Method to get all temperature Thresholds
    @Override
    public List<TemperatureThreshold> getAllTempThresholds() {
        return tempThresholdRepository.findAll();
    }

    // Method to get all temperature Thresholds by room Id
    @Override
    public List<TemperatureThreshold> getAllTempThresholdsByRoomId(String roomId) {
        System.out.println(roomId + "service IMPL");
        List<TemperatureThreshold> tempList = new ArrayList<>();
        for (TemperatureThreshold temp: tempThresholdRepository.findAll()) {
            if(temp.getRoomId().equals(roomId)){
                tempList.add(temp);
                System.out.println("equals");
            }
        }
        System.out.println(tempList);
        return tempList;
    }



    @Override
    public boolean addTempThreshold(TemperatureThreshold temperatureThreshold) throws Exception {

        System.out.println();
        if (roomRepository.findById(temperatureThreshold.getRoomId()).isEmpty()){
            throw new Exception("noRoom");
        }

        List<TemperatureThreshold> roomsThresholds = new ArrayList<>();
        for (TemperatureThreshold temp: tempThresholdRepository.findAll()) {
            if(temp.getRoomId().equals(temperatureThreshold.getRoomId())){
                roomsThresholds.add(temp);
            }
        }
        for (TemperatureThreshold temp : roomsThresholds){
            if((temperatureThreshold.getStartTime().isAfter(temp.getStartTime()) || temperatureThreshold.getStartTime().equals(temp.getStartTime())) &&   ((temperatureThreshold.getStartTime().isBefore(temp.getEndTime()))) || temperatureThreshold.getStartTime().equals(temp.getEndTime())){
                throw new Exception("insideTimeZone");
            }
            else if(temperatureThreshold.getEndTime().isAfter(temp.getStartTime()) && (temperatureThreshold.getEndTime().isBefore(temp.getEndTime())) || ((temperatureThreshold.getEndTime().equals(temp.getEndTime())) || temperatureThreshold.getEndTime().equals(temp.getStartTime()))){
                throw new Exception("insideTimeZone");
            }
            else if(temperatureThreshold.contains(temp)){
                throw new Exception("alreadyContains");
            }
        }
        if(temperatureThreshold.getStartTime()==null || temperatureThreshold.getEndTime()==null){
            throw new Exception("noTime");
        }
        else if(temperatureThreshold.getMax()<= temperatureThreshold.getMin()){
            throw new Exception("badMaxAndMin");
        }


        tempThresholdRepository.save(temperatureThreshold);
        return  true;
    }
    //Deleting threshold by Id
    @Override
    public void deleteTempThreshold(int id)
    {
        tempThresholdRepository.deleteById(id);
    }

    //Updating threshold by threshold Id
    @Override
    public void updateTempThreshold(TemperatureThreshold temperatureThreshold) {
        //Not working. add id in path
        tempThresholdRepository.updateTempThreshold(temperatureThreshold.getMax(), temperatureThreshold.getMin(), temperatureThreshold.getId());
    }

    // This method will if the temperature data from measurements is inside Min and Max threshold
    public boolean isInsideMaxAndMin(Measurement measurement, TemperatureThreshold temperatureThreshold){
        if (measurement.getTemperature() > temperatureThreshold.getMax()) {
            return false;

        } else if (measurement.getTemperature() < temperatureThreshold.getMin()) {
            return false;
        }
        return true;
    }
    // This method will if the measurement timestamp is inside Start time and End time threshold
    public boolean isInsideStartTimeEndTime(Measurement measurement,TemperatureThreshold temperatureThreshold){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(measurement.getDate());

        int measurementHour = calendar.get(Calendar.HOUR_OF_DAY);
        int measurementMinute = calendar.get(Calendar.MINUTE);

        if(temperatureThreshold.getStartTime() != null && temperatureThreshold.getEndTime() != null) {


            if (measurementHour >= temperatureThreshold.getStartTime().getHour() && measurementHour <= temperatureThreshold.getEndTime().getHour()) {
                return true;
            } else if (measurementHour == temperatureThreshold.getStartTime().getHour() || measurementHour == temperatureThreshold.getEndTime().getHour()) {
                if (measurementMinute > temperatureThreshold.getStartTime().getMinute() && measurementMinute < temperatureThreshold.getEndTime().getMinute()) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

}
