package SEP4Data.air4you.threshold;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "threshold")
public class Threshold {

    @Id
    private int thresholdId;
    private String userId = "none";
    private double minTemp;
    private double maxTemp;
    private double minHumidity;
    private double maxHumidity;
    private DateTime startTime;
    private DateTime endTime;

    public Threshold(int thresholdId, String userId, double minTemp, double maxTemp, double minHumidity, double maxHumidity, DateTime startTime, DateTime endTime) {
        this.thresholdId = thresholdId;
        this.userId = userId;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Threshold() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }
}
