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

  @PostMapping("/humidityThresholds/")
  public int addThresholdHumidity(@RequestBody HumidityThreshold humidityThreshold){
    if (humidityThresholdService.addHumidityThreshold(humidityThreshold)){
      return HttpServletResponse.SC_OK;
    }
    else{
      return  HttpServletResponse.SC_FORBIDDEN;
    }
  }

  @DeleteMapping("/humidityThresholds/{id}")
  public int deleteHumidityThreshold(@PathVariable int id){
    try {
      humidityThresholdService.deleteHumidityThreshold(id);
      System.out.println("Successfully deleted humidityThreshold with id: " + id);
      return HttpServletResponse.SC_OK;
    } catch (NullPointerException nullPointerException){
      System.out.println("Could not delete humidityThreshold because: \n " + nullPointerException.getMessage());
      return HttpServletResponse.SC_NOT_FOUND;
    } catch (Exception otherException){
      System.out.println("Could not delete humidityThreshold because: \n " + otherException.getMessage());
      return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
  }

  @DeleteMapping("/humidityThresholds/all/")
  public void deleteAllHumidityThresholds(){
    humidityThresholdService.deleteAll();
  }

}
