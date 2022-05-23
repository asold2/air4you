package SEP4Data.air4you.day;

import SEP4Data.air4you.measurement.Measurement;
import SEP4Data.air4you.room.Room;
import java.util.List;

import javax.persistence.*;
import java.util.ArrayList;
import java.sql.Date;

@Entity
@Table(name = "Day")
public class Day {
    @Id
    private int dayId;

    @ManyToOne
    private Room roomId;

    @OneToMany(mappedBy="dayId")
    private List<Measurement> measurements;
    private Date dayDate;


    public Day() {
    }

    public Day(Date dayDate) {
        this.measurements = new ArrayList<>();
        long millis=System.currentTimeMillis();
        this.dayDate = new Date(millis);
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(ArrayList<Measurement> measurements) {
        this.measurements = measurements;
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }
}
