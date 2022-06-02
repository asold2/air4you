package SEP4Data.air4you.humidityThreshold;

import SEP4Data.air4you.Notification.Data;
import SEP4Data.air4you.measurement.Measurement;
import SEP4Data.air4you.room.RoomRepository;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class HumidityThresholdServiceImpl implements IHumidityThresholdService
{
  @Autowired
  private HumidityThresholdRepository humidityThresholdRepository;

  @Autowired
  private RoomRepository roomRepository;


  // Adding humidity threshold 
  @Override
  public boolean addHumidityThreshold(HumidityThreshold humidityThreshold) throws Exception {
    System.out.println("das");
    if (roomRepository.findById(humidityThreshold.getRoomId()).isEmpty())
    {
      throw new Exception("noRoom");
    }

    List<HumidityThreshold> roomsThresholds = new ArrayList<>();
    for (HumidityThreshold temp : humidityThresholdRepository.findAll())
    {
      if (temp.getRoomId().equals(humidityThreshold.getRoomId()))
      {
        roomsThresholds.add(temp);
      }
    }
    for (HumidityThreshold temp : roomsThresholds)
    {
      if ((humidityThreshold.getStartTime().isAfter(temp.getStartTime())
              || humidityThreshold.getStartTime().equals(temp.getStartTime()))
              && ((humidityThreshold.getStartTime().isBefore(temp.getEndTime())))
              || humidityThreshold.getStartTime().equals(temp.getEndTime()))
      {
        throw new Exception("insideTimeZone");
      }
      else if (humidityThreshold.getEndTime().isAfter(temp.getStartTime())
              && (humidityThreshold.getEndTime().isBefore(temp.getEndTime())) || (
              (humidityThreshold.getEndTime().equals(temp.getEndTime()))
                      || humidityThreshold.getEndTime().equals(temp.getStartTime())))
      {
        throw new Exception("insideTimeZone");
      }
      else if (humidityThreshold.contains(temp))
      {
        throw new Exception("alreadyContains");
      }
    }
    if (humidityThreshold.getStartTime() == null
            || humidityThreshold.getEndTime() == null)
    {
      throw new Exception("noTime");
    }
    humidityThresholdRepository.save(humidityThreshold);
    return true;

  }
    // deleting humidity threshold by threshold id
  @Override
  public void deleteHumidityThreshold(int id)
  {
    humidityThresholdRepository.deleteById(id);
  }

  // Method for getting all humidity thresholds by room id
  @Override
  public List<HumidityThreshold> getAllHumidityThresholdsByRoomId(
      String roomId)
  {
    System.out.println(roomId + "service IMPL");
    List<HumidityThreshold> tempList = new ArrayList<>();

    for (HumidityThreshold temp : humidityThresholdRepository.findAll())
    {
      if (temp.getRoomId().equals(roomId))
      {
        tempList.add(temp);
        System.out.println("equals");
      }
    }
    System.out.println(tempList);
    return tempList;
  }


  // Updating humidity threshold by threshold Id
  @Override
  public void updateHumidityThreshold(HumidityThreshold humidityThreshold){
    //Not working. add id in path
    humidityThresholdRepository.updateHumidityThreshold(humidityThreshold.getMax(), humidityThreshold.getMin(), humidityThreshold.getId());
  }








}
