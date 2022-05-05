package SEP4Data.air4you.measurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MeasurementController {

    @Autowired
    private IMeasurementService measurementService;

    public MeasurementController(IMeasurementService measurementService){
        this.measurementService = measurementService;
    }

    @PostMapping("/measurement/")
    public void addMeasurement(@RequestBody Measurement measurement){
        measurementService.addMeasurement(measurement);
    }

    @GetMapping("/measurement/{roomId}")
    public List<Measurement> getMeasurement(@PathVariable int roomId){
        return measurementService.getMeasurements(roomId);
    }

    @DeleteMapping("/measurement/room/{roomId}")
    public void removeMeasurement(@PathVariable int roomId){
        measurementService.deleteAllFromRoom(roomId);
    }

    @DeleteMapping("/measurement/user/{userId}")
    public void removeMeasurement(@PathVariable String userId){
        measurementService.deleteAllFromUser(Integer.valueOf(userId));
    }

    // DO NOT USE THIS UNLESS YOU ARE READY TO HAVE NO DATA ON MEASUREMENTS
    @DeleteMapping("/measurement/all/")
    public void removeMeasurement(){
        measurementService.deleteAll();
    }








}
