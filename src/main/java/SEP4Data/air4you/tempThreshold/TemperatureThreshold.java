package SEP4Data.air4you.tempThreshold;

import SEP4Data.air4you.Notification.Notification;
import SEP4Data.air4you.common.Threshold;
import SEP4Data.air4you.measurement.IMeasurementService;

import javax.persistence.*;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Temperature_thresholds")
public class TemperatureThreshold extends Threshold {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String roomId;
    private LocalTime startTime;
    private LocalTime endTime;
    private double min;
    private double max;

    public TemperatureThreshold(){}

    public TemperatureThreshold(String roomId, LocalTime startTime, LocalTime endTime, double min, double max) {
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.min = min;
        this.max = max;
    }

    public int getId() {
        return Id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public boolean contains(TemperatureThreshold temp) {
        if(this.getStartTime().isBefore(temp.getStartTime()) && this.getEndTime().isAfter(temp.getEndTime())){
            System.out.println("Contains");
            return true;
        }
        else {
            return  false;
        }
    }

}
