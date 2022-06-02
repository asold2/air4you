package SEP4Data.air4you.humidityThreshold;

import SEP4Data.air4you.measurement.Measurement;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;

import java.util.Date;
import java.util.List;

public interface IHumidityThresholdService
{
  boolean addHumidityThreshold(HumidityThreshold humidityThreshold) throws Exception;
  void deleteHumidityThreshold(int id);
  void updateHumidityThreshold(HumidityThreshold humidityThreshold);
  List<HumidityThreshold> getAllHumidityThresholdsByRoomId(String roomId);
}
