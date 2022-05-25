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


  // This method will return List of humidity thresholds by roomId if the link is called
  @GetMapping("/humidityThresholds/{roomId}")
  public List<HumidityThreshold> getAllThresholdsByRoomIdHumidity(@PathVariable String roomId)
  {
    System.out.println(roomId + "!!!!!");
    return humidityThresholdService.getAllHumidityThresholdsByRoomId(roomId);
  }

  // This method will return List of all humidity thresholds if the link is called
  @GetMapping("/humidityThresholds/")
  public List<HumidityThreshold> getAllThrehsoldsHumidity()
  {
    return humidityThresholdService.getAllHumidityThresholds();
  }
  // This method will add humidity threshold if the link is called
  @PostMapping("/humidityThresholds/")
  public int addThresholdHumidity(@RequestBody HumidityThreshold humidityThreshold){
    try {
      if (humidityThresholdService.addHumidityThreshold(humidityThreshold)){
        return HttpServletResponse.SC_OK;
      }
    } catch (Exception e) {
      switch (e.getMessage()){
        case "noRoom":
          // 409 // There is no room to have threshold inside.
          return HttpServletResponse.SC_CONFLICT;
        case "insideTimeZone":
          // 400 // Creating threshold that is inside other threshold's startTime/endTime
          return HttpServletResponse.SC_BAD_REQUEST;
        case "alreadyContains":
          // 406 // There is already a threshold like this inside the room.
          return HttpServletResponse.SC_NOT_ACCEPTABLE;
        case "noTime":
          // 417 // startTime or/and endTime is not inside Request.
          return HttpServletResponse.SC_EXPECTATION_FAILED;
        default:
          // 500 // Something inside program is wrong.
          return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      }
    }
    return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
  }
  // This method will delete humidity threshold by Id if the link is called
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

  // This method will delete all humidity thresholds if the link is called
  @DeleteMapping("/humidityThresholds/all/")
  public void deleteAllHumidityThresholds(){
    humidityThresholdService.deleteAll();
  }

  // This method will update humidity threshold if the link is called
  @PutMapping("/humidityThresholds/")
  public void updateHumidityThreshold(@RequestBody HumidityThreshold humidityThreshold){
    humidityThresholdService.updateHumidityThreshold((humidityThreshold));
  }
}
