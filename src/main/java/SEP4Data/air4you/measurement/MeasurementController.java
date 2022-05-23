package SEP4Data.air4you.measurement;

import SEP4Data.air4you.room.RoomService;
import SEP4Data.air4you.threshold.ISendThresholdToGateway;
import SEP4Data.air4you.threshold.Threshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class MeasurementController {

    @Autowired
    private IMeasurementService measurementService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ISendThresholdToGateway iSendThresholdToGateway;

    public MeasurementController(IMeasurementService measurementService){
        this.measurementService = measurementService;
    }

    @PostMapping("/measurement/")
    public @ResponseBody
    Threshold addMeasurement(@RequestBody Measurement measurement) throws IOException {
        System.out.println(measurement.toString() + " add measurement" );
//        iSendTempThresholdToGateway.sendTempThresholdToGateway(measurementService.addMeasurement(measurement));
        Threshold threshold = measurementService.addMeasurement(measurement);






        return  threshold;

    }


    @GetMapping("/measurement/{roomId}")
    public List<Measurement> getMeasurement(@PathVariable String roomId){
        return measurementService.getMeasurements(roomId);
    }

    @DeleteMapping("/measurement/room/{roomId}")
    public void removeMeasurementFromRoom(@PathVariable String roomId){
        measurementService.deleteAllFromRoom(roomId);
    }

    @DeleteMapping("/measurement/user/{userId}")
    public void removeMeasurementByUser(@PathVariable String userId){
        measurementService.deleteAllFromUser(userId);
    }

    // DO NOT USE THIS UNLESS YOU ARE READY TO HAVE NO DATA ON MEASUREMENTS
    @DeleteMapping("/measurement/all/")
    public void removeMeasurement(){
        measurementService.deleteAll();
    }


}
