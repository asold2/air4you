package SEP4Data.air4you.measurement;

import SEP4Data.air4you.threshold.Threshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class MeasurementController {

    @Autowired
    private IMeasurementService measurementService;


    // This method will add measurement if the link is called
    @PostMapping("/measurement/")
    public void addMeasurement(@RequestBody Measurement measurement){
        measurementService.addMeasurement(measurement);
    }

    // This method will delete measurement by roomId if the link is called
    @DeleteMapping("/measurement/room/{roomId}")
    public int removeMeasurementFromRoom(@PathVariable String roomId){
        if(measurementService.deleteAllFromRoom(roomId)){
            return HttpServletResponse.SC_OK;
        } else {
            return HttpServletResponse.SC_NOT_FOUND;
        }
    }


    //When this link(method) is called it takes room id and it will return List of average temperatures for each day from last week
    @GetMapping("/measurement/averageTemp/{roomId}")
    public List<Double> getAverageTemp(@PathVariable String roomId){
        return measurementService.getAverageTemp(roomId);
    }

    //When this link(method) is called it takes room id and it will return List of average humidity for each day from last week
    @GetMapping("/measurement/averageHumidity/{roomId}")
    public List<Double> getAverageHumidity(@PathVariable String roomId){

        return measurementService.getAverageHumidity(roomId);

    }

    //When this link(method) is called it takes room id and it will return List of average Co2 for each day from last week
    @GetMapping("/measurement/averageCo2/{roomId}")
    public List<Double> getAverageCo2(@PathVariable String roomId){

        return measurementService.getAverageCo2(roomId);

    }
    @GetMapping("/measurement/week/{userId}")
    public List<Measurement> getMeasurementByUserAndRoomIdWeek(@PathVariable String userId)
    {
        return measurementService.getMeasurementByUserAndRoomIdWeek(userId);
    }


}
