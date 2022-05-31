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
    public boolean updateTempThreshold(TemperatureThreshold temperatureThreshold) {
        //Not working. add id in path
        if (tempThresholdRepository.existsById(temperatureThreshold.getId())) {
            tempThresholdRepository.updateTempThreshold(temperatureThreshold.getMax(), temperatureThreshold.getMin(), temperatureThreshold.getStartTime(), temperatureThreshold.getEndTime(), temperatureThreshold.getId());
            return true;
        } else {
            return false;
        }
    }


}
