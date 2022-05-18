package SEP4Data.air4you.co2Threshold;

import SEP4Data.air4you.room.RoomRepository;
import SEP4Data.air4you.tempThreshold.ISendTempThresholdToGateway;
import SEP4Data.air4you.tempThreshold.TempThresholdRepository;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CO2ThresholdServiceImpl implements ICO2ThresholdService
{
  @Autowired
  private CO2ThresholdRepository co2ThresholdRepository;
  @Autowired
  private RoomRepository roomRepository;



  @Override public CO2Threshold getCO2Threshold()
  {
    return co2ThresholdRepository.findAll().get(0);
  }

  @Override public CO2Threshold getCO2ThresholdsByRoomId(String roomId)
  {
    System.out.println(roomId + "service IMPL");
    List<CO2Threshold> co2ThresholdsList = new ArrayList<>();
    for (CO2Threshold temp: co2ThresholdRepository.findAll()) {
      if(temp.getRoomID().equals(roomId)){
        co2ThresholdsList.add(temp);
        System.out.println("equals");
      }
    }
    System.out.println(co2ThresholdsList.get(0));
    return co2ThresholdsList.get(0);
  }
}
