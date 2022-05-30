package SEP4Data.air4you.threshold;

import org.springframework.web.bind.annotation.*;

@RestController
public class ThresholdController {

    private final ThresholdService thresholdService;

    public ThresholdController(ThresholdService thresholdService){
        this.thresholdService = thresholdService;
    }

    // Endpoint for when android app wants to update threshold for user.
    @PutMapping("/threshold/{userId}/")
    public Threshold updateThreshold(@PathVariable int userId, @RequestBody Threshold threshold){

        //TODO change threshold in database and then send HTTP message to GateWay Application about this update.

        return null;
    }





}
