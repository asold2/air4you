package SEP4Data.air4you.humidityThreshold;

import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class HumidityThresholdController
{
  @Autowired
  private IHumidityThresholdService humidityThresholdService;

  @GetMapping("/thresholds/{roomId}")
  public List<HumidityThreshold> getAllThresholdsByRoomIdHumidity(@PathVariable String roomId)
  {
    System.out.println(roomId + "!!!!!");
    return humidityThresholdService.getAllHumidityThresholdsByRoomId(roomId);
  }

  @GetMapping("/all/thresholds/")
  public List<HumidityThreshold> getAllThrehsoldsHumidity()
  {
    return humidityThresholdService.getAllHumidityThresholds();
  }

  @PostMapping("/new/threshold/")
  public int addThresholdHumidity(@RequestBody HumidityThreshold humidityThreshold){
    if (humidityThresholdService.addHumidityThreshold(humidityThreshold)){
      return HttpServletResponse.SC_OK;
    }
    else{
      return  HttpServletResponse.SC_FORBIDDEN;
    }
  }

  @DeleteMapping("/remove/humidityThr/{roomId}/{thresholdId}")
  public void deleteHumidityThreshold(@PathVariable String roomId, @PathVariable int thresholdId){
    System.out.println(roomId + "!!!!!!!!");
    System.out.println(thresholdId + "AAAAAAAa");
    humidityThresholdService.deleteHumidityThreshold(roomId, thresholdId);
  }

  @DeleteMapping("removal/thresh")
  public void deleteAll(){
    humidityThresholdService.deleteAll();
  }
}
