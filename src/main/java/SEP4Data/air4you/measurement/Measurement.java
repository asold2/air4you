package SEP4Data.air4you.measurement;



import SEP4Data.air4you.day.Day;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="measurement")
public class Measurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private Date date;
    private String roomId;

    @ManyToOne
    private Day dayId;
    private double temperature;
    private double humidity;
    private double co2;
    private Boolean co2Exceeded = false;
    private Boolean temperatureExceeded = false;
    private Boolean humidityExceeded = false;


    public Measurement(Date date, double temperature, double humidity, double co2){
        this.date = date;
        this.roomId = dayId.getRoomId().getRoomId();
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
    }

    public Day getDayId() {
        return dayId;
    }

    public void setDayId(Day dayId) {
        this.dayId = dayId;
    }

    public Boolean getCo2Exceeded() {
        return co2Exceeded;
    }

    public void setCo2Exceeded(Boolean co2Exceeded) {
        this.co2Exceeded = co2Exceeded;
    }

    public Boolean getTemperatureExceeded() {
        return temperatureExceeded;
    }

    public void setTemperatureExceeded(Boolean temperatureExceeded) {
        this.temperatureExceeded = temperatureExceeded;
    }

    public Boolean getHumidityExceeded() {
        return humidityExceeded;
    }

    public void setHumidityExceeded(Boolean humidityExceeded) {
        this.humidityExceeded = humidityExceeded;
    }

    public boolean isCo2Exceeded() {
        return co2Exceeded;
    }

    public void setCo2Exceeded(boolean co2Exceeded) {
        this.co2Exceeded = co2Exceeded;
    }

    public boolean isTemperatureExceeded() {
        return temperatureExceeded;
    }

    public void setTemperatureExceeded(boolean temperatureExceeded) {
        this.temperatureExceeded = temperatureExceeded;
    }

    public boolean isHumidityExceeded() {
        return humidityExceeded;
    }

    public void setHumidityExceeded(boolean humidityExceeded) {
        this.humidityExceeded = humidityExceeded;
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
