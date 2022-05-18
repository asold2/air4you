package SEP4Data.air4you.co2Threshold;

import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CO2Controller
{
  @Autowired
  private ICO2ThresholdService thresholdService;

  @GetMapping("/co2threshold/{roomId}")
  public CO2Threshold getCO2ThresholdByRoomId(@PathVariable String roomId){
    System.out.println(roomId + "!!!!!");
    return thresholdService.getCO2ThresholdsByRoomId(roomId);
  }
  @GetMapping("/all/co2thresholds/")
  public CO2Threshold getAllCO2Thresholds(){
    return thresholdService.getCO2Threshold();
  }
}
