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

  @GetMapping("/humiditythresholds/{roomId}")
  public List<HumidityThreshold> getAllThresholdsByRoomIdHumidity(@PathVariable String roomId)
  {
    System.out.println(roomId + "!!!!!");
    return humidityThresholdService.getAllHumidityThresholdsByRoomId(roomId);
  }

  @GetMapping("/all/humiditythresholds/")
  public List<HumidityThreshold> getAllThrehsoldsHumidity()
  {
    return humidityThresholdService.getAllHumidityThresholds();
  }

  @PostMapping("/new/humiditythresholds/")
  public int addThresholdHumidity(@RequestBody HumidityThreshold humidityThreshold){
    if (humidityThresholdService.addHumidityThreshold(humidityThreshold)){
      return HttpServletResponse.SC_OK;
    }
    else{
      return  HttpServletResponse.SC_FORBIDDEN;
    }
  }

  @DeleteMapping("/remove/humiditythresholds/{roomId}/{thresholdId}")
  public void deleteHumidityThreshold(@PathVariable String roomId, @PathVariable int thresholdId){
    System.out.println(roomId + "!!!!!!!!");
    System.out.println(thresholdId + "AAAAAAAa");
    humidityThresholdService.deleteHumidityThreshold(roomId, thresholdId);
  }

  @DeleteMapping("/removal/humiditythresholds")
  public void deleteAll(){
    humidityThresholdService.deleteAll();
  }
}
