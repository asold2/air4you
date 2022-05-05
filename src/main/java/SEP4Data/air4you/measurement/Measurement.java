package SEP4Data.air4you.measurement;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="measurement")
public class Measurement {

    @Id
    private DateTime timestamp;
    private int roomId;
    private double temperature;
    private double humidity;
    private double co2;

    public Measurement(DateTime timestamp, int  roomId, double temperature, double humidity, double co2){
        this.timestamp = timestamp;
        this.roomId = roomId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
    }

    public Measurement() {

    }

    public DateTime getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }
}
