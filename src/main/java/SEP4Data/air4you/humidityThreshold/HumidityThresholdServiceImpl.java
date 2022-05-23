package SEP4Data.air4you.humidityThreshold;

import SEP4Data.air4you.room.RoomRepository;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HumidityThresholdServiceImpl implements IHumidityThresholdService
{
  @Autowired
  private HumidityThresholdRepository humidityThresholdRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Override
  public List<HumidityThreshold> getAllHumidityThresholds()
  {
    return humidityThresholdRepository.findAll();
  }

  @Override
  public boolean addHumidityThreshold(HumidityThreshold humidityThreshold)
  {
    if (roomRepository.findById(humidityThreshold.getRoomId()).isEmpty())
    {
      System.out.println("hum1");
      return false;
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
        System.out.println("Humdity add 3");
        return false;
      }
      else if (humidityThreshold.getEndTime().isAfter(temp.getStartTime())
          && (humidityThreshold.getEndTime().isBefore(temp.getEndTime())) || (
          (humidityThreshold.getEndTime().equals(temp.getEndTime()))
              || humidityThreshold.getEndTime().equals(temp.getStartTime())))
      {
        System.out.println("Humdity add 4");
        return false;
      }
      else if (humidityThreshold.contains(temp))
      {
        System.out.println("Humdity add 5");
        return false;
      }
    }
    if (humidityThreshold.getStartTime() == null
        || humidityThreshold.getEndTime() == null)
    {
      System.out.println("Humdity add 6");
      return false;
    }
    humidityThresholdRepository.save(humidityThreshold);
    System.out.println("Humdity add 7");
    return true;

  }

  @Override
  public void deleteHumidityThreshold(int id)
  {
    humidityThresholdRepository.deleteById(id);
  }

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

  @Override
  public void deleteAll()
  {
    humidityThresholdRepository.deleteAll();
  }

  @Override
  public void updateHumidityThreshold(HumidityThreshold humidityThreshold){
    System.out.println(humidityThreshold.getId() + "!!!!!!!!!!!!!!!!!!!!!!!!");
    //Not working. add id in path
    humidityThresholdRepository.updateHumidityThreshold(humidityThreshold.getMax(), humidityThreshold.getMin(), humidityThreshold.getId());
  }
}
