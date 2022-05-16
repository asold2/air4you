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

    @GetMapping("/tempthresholds/{roomId}")
    public List<TemperatureThreshold> getAllThrehsoldsByRoomId(@PathVariable String roomId){
        System.out.println(roomId + "!!!!!");
        return tempThresholdService.getAllTempThresholdsByRoomId(roomId);
    }
    @GetMapping("/all/tempthresholds/")
    public List<TemperatureThreshold> getAllThrehsolds(){
        return tempThresholdService.getAllTempThresholds();
    }


    @PostMapping("/new/tempthresholds/")
    public int addThreshold(@RequestBody TemperatureThreshold temperatureThreshold){
        if (tempThresholdService.addTempThreshold(temperatureThreshold)){
            return HttpServletResponse.SC_OK;
        }
        else{
            return  HttpServletResponse.SC_FORBIDDEN;
        }
    }

    @DeleteMapping("/remove/tempthresholds/{roomId}/{thresholdId}")
    public void deleteTempThreshold(@PathVariable String roomId, @PathVariable int thresholdId){
        System.out.println(roomId + "!!!!!!!!");
        System.out.println(thresholdId + "AAAAAAAa");
        tempThresholdService.deleteTempThreshold(roomId, thresholdId);
    }


    @DeleteMapping("/removal/tempthresholds")
    public void deleteAll(){
        tempThresholdService.deleteAll();
    }

}
