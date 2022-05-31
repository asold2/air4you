package SEP4Data.air4you.measurement;

import SEP4Data.air4you.room.RoomService;
import SEP4Data.air4you.threshold.Threshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class MeasurementController {

    @Autowired
    private IMeasurementService measurementService;


    // This method will add measurement if the link is called
    @PostMapping("/measurement/")
    public @ResponseBody
    Threshold addMeasurement(@RequestBody Measurement measurement){
        return measurementService.addMeasurement(measurement);
    }

    //Todo delete
    // This method will get all measurements by roomId if the link is called
    @GetMapping("/measurement/{roomId}")
    public List<Measurement> getMeasurement(@PathVariable String roomId){
        return measurementService.getMeasurements(roomId);
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

    //Todo delete
    // This method will delete measurement by userId if the link is called
    @DeleteMapping("/measurement/user/{userId}")
    public void removeMeasurementByUser(@PathVariable String userId){
        measurementService.deleteAllFromUser(userId);
    }

    //Todo delete
    //Method for deleting all measurements
    // DO NOT USE THIS UNLESS YOU ARE READY TO HAVE NO DATA ON MEASUREMENTS
    @DeleteMapping("/measurement/all/")
    public void removeMeasurement(){
        measurementService.deleteAll();
    }


    //Todo delete
    // Receive DATE and roomId and send all measurements.
    @GetMapping("/measurement/{date}/{roomId}")
    public List<Measurement> getMeasurementsByDateAndRoomId(@PathVariable String date, @PathVariable String roomId){
        return measurementService.getMeasurementByDateAndRoomId(date, roomId);
    }

    //Todo delete
    @GetMapping("/measurement/{startDate}/{endDate}/{roomId}")
    public List<Measurement> getMeasurementsBetweenDates(@PathVariable String startDate, @PathVariable String endDate, @PathVariable String roomId){
        return measurementService.getMeasurementsBetweenDates(startDate, endDate, roomId);
    }

    @GetMapping("/measurement/week/{userId}")
    public List<Measurement> getMeasurementByUserAndRoomIdWeek(@PathVariable String userId)
    {
        return measurementService.getMeasurementByUserAndRoomIdWeek(userId);
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


}
