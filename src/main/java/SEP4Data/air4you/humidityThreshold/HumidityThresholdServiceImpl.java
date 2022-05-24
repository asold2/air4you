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

  @Override
  public List<HumidityThreshold> getAllHumidityThresholds()
  {
    return humidityThresholdRepository.findAll();
  }

  // Adding humidity threshold 
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

  // Method for deleting all humidity thresholds
  @Override
  public void deleteAll()
  {
    humidityThresholdRepository.deleteAll();
  }

  // Updating humidity threshold by threshold Id
  @Override
  public void updateHumidityThreshold(HumidityThreshold humidityThreshold){
    System.out.println(humidityThreshold.getId() + "!!!!!!!!!!!!!!!!!!!!!!!!");
    //Not working. add id in path
    humidityThresholdRepository.updateHumidityThreshold(humidityThreshold.getMax(), humidityThreshold.getMin(), humidityThreshold.getId());
  }

  // This method runs through all humidity thresholds in a specific room and find one threshold by the time
  @Override
  public HumidityThreshold returnCurrentHumidityThreshold(String roomId, Date measurementDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(measurementDate);

    // Change Measurement Date type into LocalTime dataType.
    LocalTime measurementTime = LocalTime.of(
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            calendar.get(Calendar.SECOND));

    HumidityThreshold humidityThreshold = null;
    for (HumidityThreshold temp: getAllHumidityThresholdsByRoomId(roomId)) {
      if (temp.getStartTime().isBefore(measurementTime) && temp.getEndTime().isAfter(measurementTime)){
        humidityThreshold = temp;
      }
    }
    if(humidityThreshold == null){
      humidityThreshold = new HumidityThreshold(0,0);
    }
    return humidityThreshold;
  }

// This method will if the humidity data from measurements is inside Min and Max threshold
  public boolean isInsideMaxAndMin(Measurement measurement, HumidityThreshold humidityThreshold){
    if (measurement.getHumidity() > humidityThreshold.getMax()) {
      return false;

    } else if (measurement.getHumidity() < humidityThreshold.getMin()) {
      return false;
    }
    return true;
  }

  // This method will if the measurement timestamp is inside Start time and End time threshold
  public boolean isInsideStartTimeEndTime(Measurement measurement,HumidityThreshold humidityThreshold){

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(measurement.getDate());

    int measurementHour = calendar.get(Calendar.HOUR_OF_DAY);
    int measurementMinute = calendar.get(Calendar.MINUTE);

    if(humidityThreshold.getStartTime() != null && humidityThreshold.getEndTime() != null) {


      if (measurementHour >= humidityThreshold.getStartTime().getHour() && measurementHour <= humidityThreshold.getEndTime().getHour()) {
        return true;
      } else if (measurementHour == humidityThreshold.getStartTime().getHour() || measurementHour == humidityThreshold.getEndTime().getHour()) {
        if (measurementMinute > humidityThreshold.getStartTime().getMinute() && measurementMinute < humidityThreshold.getEndTime().getMinute()) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

  // This method will check "isInsideMaxAndMin" and "isInsideStartTimeEndTime" methods
  @Override
  public Measurement isInsideThreshold(Measurement measurement, HumidityThreshold humidityThreshold){
    if (isInsideStartTimeEndTime(measurement,humidityThreshold)){
      if (isInsideMaxAndMin(measurement, humidityThreshold)){
        measurement.setHumidityExceeded(false);
      }
    }
    return measurement;
  }





}
