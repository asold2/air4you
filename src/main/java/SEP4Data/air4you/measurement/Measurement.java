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
}
