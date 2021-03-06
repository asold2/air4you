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

    // All temperature thresholds for specific room are returned if this link is called
    @GetMapping("/temperatureThresholds/{roomId}")
    public List<TemperatureThreshold> getAllTempThresholdsByRoomId(@PathVariable String roomId){
        return tempThresholdService.getAllTempThresholdsByRoomId(roomId);
    }


    // Temperature threshold is added if this link is called
    @PostMapping("/temperatureThresholds/")
    public int addTempThreshold(@RequestBody TemperatureThreshold temperatureThreshold){
        System.out.println("");
        try {
            if (tempThresholdService.addTempThreshold(temperatureThreshold)) {
                return HttpServletResponse.SC_OK;
            }
        }catch (Exception e) {
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
                case "badMaxAndMin":
                    // 403 // Maximum is less than minimum or minimum is greater than maximum.
                    return HttpServletResponse.SC_FORBIDDEN;
                default:
                    // 500 // Something inside program is wrong.
                    return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            }
        }
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    // Deleting temperature threshold by id
    @DeleteMapping("/temperatureThresholds/{id}")
    public int deleteTempThreshold(@PathVariable int thresholdId){
        try {
            tempThresholdService.deleteTempThreshold(thresholdId);
            return HttpServletResponse.SC_OK;
        } catch (NullPointerException nullPointerException){
            return HttpServletResponse.SC_NOT_FOUND;
        } catch (Exception otherException){
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
    }


    // You can Update temperature threshold if link is called
    @PutMapping("/temperatureThresholds/")
    public int updateTempThreshold(@RequestBody TemperatureThreshold temperatureThreshold){
        if(tempThresholdService.updateTempThreshold(temperatureThreshold)){
            return HttpServletResponse.SC_OK;
        } else {
            return HttpServletResponse.SC_BAD_REQUEST;
        }

    }

}
