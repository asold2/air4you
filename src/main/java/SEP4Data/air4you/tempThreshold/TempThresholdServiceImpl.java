package SEP4Data.air4you.tempThreshold;

import SEP4Data.air4you.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TempThresholdServiceImpl implements ITempThresholdService{

    @Autowired
    private TempThresholdRepository tempThresholdRepository;
    @Autowired
    private RoomRepository roomRepository;

//    @Autowired
//    private ISendTempThresholdToGateway sendTempThresholdToGateway;

    @Override
    public List<TemperatureThreshold> getAllTempThresholds() {
        return tempThresholdRepository.findAll();
    }

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
    public void deleteAll() {
        tempThresholdRepository.deleteAll();
    }


    @Override
    public boolean addTempThreshold(TemperatureThreshold temperatureThreshold) {

        System.out.println();
        if (roomRepository.findById(temperatureThreshold.getRoomId()).isEmpty()){
            return false;
        }

        List<TemperatureThreshold> roomsThresholds = new ArrayList<>();
        for (TemperatureThreshold temp: tempThresholdRepository.findAll()) {
            if(temp.getRoomId().equals(temperatureThreshold.getRoomId())){
                roomsThresholds.add(temp);
            }
        }
        for (TemperatureThreshold temp : roomsThresholds){
            if((temperatureThreshold.getStartTime().isAfter(temp.getStartTime()) || temperatureThreshold.getStartTime().equals(temp.getStartTime())) &&   ((temperatureThreshold.getStartTime().isBefore(temp.getEndTime()))) || temperatureThreshold.getStartTime().equals(temp.getEndTime())){
                return false;
            }
            else if(temperatureThreshold.getEndTime().isAfter(temp.getStartTime()) && (temperatureThreshold.getEndTime().isBefore(temp.getEndTime())) || ((temperatureThreshold.getEndTime().equals(temp.getEndTime())) || temperatureThreshold.getEndTime().equals(temp.getStartTime()))){
                return false;
            }
            else if(temperatureThreshold.contains(temp)){
                return false;
            }
        }
        if(temperatureThreshold.getStartTime()==null || temperatureThreshold.getEndTime()==null){
            return false;
        }
        else if(temperatureThreshold.getMax()<= temperatureThreshold.getMin()){
            return false;
        }


        tempThresholdRepository.save(temperatureThreshold);
        return  true;
    }

    public void deleteTempThreshold(int id)
    {
        tempThresholdRepository.deleteById(id);
    }

    @Override
    public void updateTempThreshold(TemperatureThreshold temperatureThreshold) {
        System.out.println(temperatureThreshold.getId() + "!!!!!!!!!!!!!!!!!!!!");
        //Not working. add id in path
        tempThresholdRepository.updateTempThreshold(temperatureThreshold.getMax(), temperatureThreshold.getMin(), temperatureThreshold.getId());
    }



}
