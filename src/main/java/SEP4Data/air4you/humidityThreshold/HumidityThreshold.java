package SEP4Data.air4you.humidityThreshold;

import SEP4Data.air4you.threshold.Threshold;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;

@Entity
@Table(name = "Humidity_thresholds")
public class HumidityThreshold
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int Id;
  private String roomId;
  private LocalTime startTime;
  private LocalTime endTime;
  private double min;
  private double max;

  public HumidityThreshold(){
    this.max = 0;
    this.min = 0;
  }
  public HumidityThreshold(double max, double min){
    this.max = max;
    this.min = min;
  }

  public HumidityThreshold(String roomId, LocalTime startTime, LocalTime endTime, double min, double max)
  {
    this.roomId = roomId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.min = min;
    this.max = max;
  }

  public HumidityThreshold(int id, Time startTime, double max, double min, String roomId, Time endTime){
    this.Id = id;
    this.endTime = endTime.toLocalTime();
    this.max = max;
    this.min = min;
    this.startTime = startTime.toLocalTime();
    this.roomId = roomId;
  }

  public void setId(int id) {
    Id = id;
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

  public boolean contains(HumidityThreshold temp)
  {
    if(this.getStartTime().isBefore(temp.getStartTime()) && this.getEndTime().isAfter(temp.getEndTime())){
      System.out.println("Contains");
      return true;
    }
    else {
      return  false;
    }
  }
}
