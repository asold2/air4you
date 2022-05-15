package SEP4Data.air4you.humidityThreshold;

import SEP4Data.air4you.tempThreshold.TemperatureThreshold;

import java.util.List;

public interface IHumidityThresholdService
{
  List<HumidityThreshold> getAllHumidityThresholds();
  boolean addHumidityThreshold(HumidityThreshold humidityThreshold);
  void deleteHumidityThreshold(String roomId, int thresholdId);
  void updateHumidityThreshold(int thresholdId, double max, double min);
  List<HumidityThreshold> getAllHumidityThresholdsByRoomId(String roomId);
  void deleteAll();
}
