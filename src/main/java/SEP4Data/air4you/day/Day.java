package SEP4Data.air4you.day;

import SEP4Data.air4you.measurement.Measurement;
import SEP4Data.air4you.room.Room;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
public class Day implements Serializable {
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private Date dayDate;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @OneToMany(mappedBy = "day")
    private List<Measurement> measurements;


    public Day() {
    }

    public Day(Date dayDate) {
        long millis=System.currentTimeMillis();
        this.dayDate = new Date(millis);
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public String getId() {
        return id;
    }

    public void setId(String dayId) {
        this.id = id;
    }


    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }
}
