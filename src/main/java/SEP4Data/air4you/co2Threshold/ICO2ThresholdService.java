package SEP4Data.air4you.co2Threshold;

import java.util.List;

public interface ICO2ThresholdService
{
  //Do we need it with roomID or without roomID?
  CO2Threshold getCO2Threshold();
  CO2Threshold getCO2ThresholdsByRoomId(String roomId);

}
