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
  public boolean addHumidityThreshold(
      HumidityThreshold humidityThreshold)
  {
    System.out.println();
    if (roomRepository.findById(humidityThreshold.getRoomId()).isEmpty())
    {
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
        return false;
      }
      else if (humidityThreshold.getEndTime().isAfter(temp.getStartTime())
          && (humidityThreshold.getEndTime().isBefore(temp.getEndTime())) || (
          (humidityThreshold.getEndTime().equals(temp.getEndTime()))
              || humidityThreshold.getEndTime().equals(temp.getStartTime())))
      {
        return false;
      }
      else if (humidityThreshold.contains(temp))
      {
        return false;
      }
    }
    if (humidityThreshold.getStartTime() == null
        || humidityThreshold.getEndTime() == null)
    {
      return false;
    }
    humidityThresholdRepository.save(humidityThreshold);
    return true;

  }

  @Override
  public void deleteHumidityThreshold(int id)
  {
    humidityThresholdRepository.deleteById(id);
  }

  @Override
  public void updateHumidityThreshold(int thresholdId, double max,
      double min)
  {

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
}
