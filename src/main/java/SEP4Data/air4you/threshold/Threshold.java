package SEP4Data.air4you.threshold;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "threshold")
public class Threshold implements Serializable {

    @Id
    private int thresholdId;
    private String roomId = "none";
    private double minTemp = 0;
    private double maxTemp = 0;
    private double minHumidity = 0;
    private double maxHumidity = 0;

    public Threshold( String roomId, double minTemp, double maxTemp, double minHumidity, double maxHumidity) {
        this.roomId = roomId;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;

    }

    public Threshold() {

    }

    public String getUserId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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


}
