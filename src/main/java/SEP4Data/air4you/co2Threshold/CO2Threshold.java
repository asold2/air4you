package SEP4Data.air4you.co2Threshold;

import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "CO2_threshold")
public class CO2Threshold implements Serializable
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int thresholdId;
  private String roomID;
  private String userId;
  private double maxCO2Value;
  private LocalTime startTime;
  private LocalTime endTime;

  public CO2Threshold(){}

  public CO2Threshold(int thresholdId,String userId, double maxCO2Value, LocalTime startTime, LocalTime endTime, String roomID)
  {
    this.thresholdId=thresholdId;
    this.userId=userId;
    this.maxCO2Value = maxCO2Value;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public int getThresholdId()
  {
    return thresholdId;
  }

  public void setThresholdId(int thresholdId)
  {
    this.thresholdId = thresholdId;
  }

  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public double getMaxCO2Value()
  {
    return maxCO2Value;
  }

  public void setMaxCO2Value(double maxCO2Value)
  {
    this.maxCO2Value = maxCO2Value;
  }

  public LocalTime getStartTime()
  {
    return startTime;
  }

  public void setStartTime(LocalTime startTime)
  {
    this.startTime = startTime;
  }

  public LocalTime getEndTime()
  {
    return endTime;
  }

  public void setEndTime(LocalTime endTime)
  {
    this.endTime = endTime;
  }

  public String getRoomID()
  {
    return roomID;
  }

  public void setRoomID(String roomID)
  {
    this.roomID = roomID;
  }

  public boolean contains(CO2Threshold temp) {
    if(this.getStartTime().isBefore(temp.getStartTime()) && this.getEndTime().isAfter(temp.getEndTime())){
      System.out.println("Contains");
      return true;
    }
    else {
      return  false;
    }
  }
}
