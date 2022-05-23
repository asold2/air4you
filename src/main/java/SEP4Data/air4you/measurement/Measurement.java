package SEP4Data.air4you.measurement;


import SEP4Data.air4you.day.Day;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Measurement implements Serializable {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "day_id", referencedColumnName = "id")
    private Day day;



    private String roomId;
    private Date date;
    private double temperature;
    private double humidity;
    private double co2;
    private Boolean co2Exceeded = false;
    private Boolean temperatureExceeded = false;
    private Boolean humidityExceeded = false;


    public Measurement(Date date, double temperature, double humidity, double co2, String roomId) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
        this.roomId = roomId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
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


    public Date getDate() {
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
}
