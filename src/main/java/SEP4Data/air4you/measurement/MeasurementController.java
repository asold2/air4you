package SEP4Data.air4you.measurement;

import SEP4Data.air4you.common.Threshold;
import SEP4Data.air4you.room.RoomService;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class MeasurementController {

    @Autowired
    private IMeasurementService measurementService;
    @Autowired
    private RoomService roomService;

    public MeasurementController(IMeasurementService measurementService){
        this.measurementService = measurementService;
    }

    @PostMapping("/measurement/")
    public @ResponseBody
    Threshold addMeasurement(@RequestBody Measurement measurement){
        System.out.println(measurement.toString());
        return measurementService.addMeasurement(measurement);
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
