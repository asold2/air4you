package SEP4Data.air4you.measurement;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="measurement")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private Date date;
    private String roomId;
    private double temperature;
    private double humidity;
    private double co2;

    public Measurement(Date timestamp, String  roomId, double temperature, double humidity, double co2){
        this.date = timestamp;
        this.roomId = roomId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
    }

    public Measurement() {

    }

    public int getId() {
        return Id;
    }


    public Date getDate(){
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setId(int id) {
        Id = id;
    }
}
