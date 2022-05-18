package SEP4Data.air4you.tempThreshold;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;
import java.util.List;

@RestController
public class TempThresholdController {

    @Autowired
    private ITempThresholdService tempThresholdService;

    @GetMapping("/tempThresholds/{roomId}")
    public List<TemperatureThreshold> getAllThresholdsByRoomId(@PathVariable String roomId){
        System.out.println(roomId + "!!!!!");
        return tempThresholdService.getAllTempThresholdsByRoomId(roomId);
    }
    @GetMapping("/all/tempThresholds/")
    public List<TemperatureThreshold> getAllThresholds(){
        return tempThresholdService.getAllTempThresholds();
    }


    @PostMapping("/new/tempThresholds/")
    public int addThreshold(@RequestBody TemperatureThreshold temperatureThreshold){
        if (tempThresholdService.addTempThreshold(temperatureThreshold)){
            return HttpServletResponse.SC_OK;
        }
        else{
            return  HttpServletResponse.SC_FORBIDDEN;
        }
    }

    @DeleteMapping("/tempThresholds/{id}")
    public int deleteTempThreshold(@PathVariable int id){
        try {
            tempThresholdService.deleteTempThreshold(id);
            System.out.println("Successfully deleted temperatureThreshold with id: " + id);
            return HttpServletResponse.SC_OK;
        } catch (NullPointerException nullPointerException){
            System.out.println("Could not delete temperatureThreshold because: \n " + nullPointerException.getMessage());
            return HttpServletResponse.SC_NOT_FOUND;
        } catch (Exception otherException){
            System.out.println("Could not delete temperatureThreshold because: \n " + otherException.getMessage());
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
    }


    @DeleteMapping("/removal/tempThresholds")
    public void deleteAll(){
        tempThresholdService.deleteAll();
    }

    @PutMapping("/updating/tempThreshold/")
    public void updateTempThreshold(@RequestBody TemperatureThreshold temperatureThreshold){
        tempThresholdService.updateTempThreshold(temperatureThreshold);
    }

}
