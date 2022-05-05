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





}
