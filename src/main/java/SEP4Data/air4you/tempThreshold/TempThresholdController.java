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

    @GetMapping("/temperatureThresholds/{roomId}")
    public List<TemperatureThreshold> getAllTempThresholdsByRoomId(@PathVariable String roomId){
        System.out.println(roomId + "!!!!!");
        return tempThresholdService.getAllTempThresholdsByRoomId(roomId);
    }
    @GetMapping("/temperatureThresholds/")
    public List<TemperatureThreshold> getAllTempThresholds(){
        return tempThresholdService.getAllTempThresholds();
    }


    @PostMapping("/temperatureThresholds/")
    public int addTempThreshold(@RequestBody TemperatureThreshold temperatureThreshold){
        if (tempThresholdService.addTempThreshold(temperatureThreshold)){
            return HttpServletResponse.SC_OK;
        }
        else{
            return  HttpServletResponse.SC_FORBIDDEN;
        }
    }

    @DeleteMapping("/temperatureThresholds/{id}")
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


    @DeleteMapping("/temperatureThresholds/all/")
    public void deleteAllTempThresholds(){
        tempThresholdService.deleteAll();
    }

    @PutMapping("/temperatureThresholds/")
    public void updateTempThreshold(@RequestBody TemperatureThreshold temperatureThreshold){
        tempThresholdService.updateTempThreshold(temperatureThreshold);

    }

}
