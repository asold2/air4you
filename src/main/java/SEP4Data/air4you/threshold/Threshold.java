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
}
