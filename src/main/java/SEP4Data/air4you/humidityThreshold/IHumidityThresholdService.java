package SEP4Data.air4you.humidityThreshold;

import SEP4Data.air4you.tempThreshold.TemperatureThreshold;

import java.util.List;

public interface IHumidityThresholdService
{
  List<HumidityThreshold> getAllHumidityThresholds();
  boolean addHumidityThreshold(HumidityThreshold humidityThreshold);
  void deleteHumidityThreshold(int id);
  void updateHumidityThreshold(HumidityThreshold humidityThreshold);
  List<HumidityThreshold> getAllHumidityThresholdsByRoomId(String roomId);
  void deleteAll();
}
