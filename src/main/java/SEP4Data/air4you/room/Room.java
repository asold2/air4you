package SEP4Data.air4you.room;

import SEP4Data.air4you.measurement.Measurement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="room")
public class Room {
    @Id
    private String roomId;
    private String name;
    private String userId = "none";
    private LocalDateTime registrationDate;
    @OneToMany(mappedBy="roomId")
    private List<Measurement> measurements;


    public Room() {
    }

    public Room(String id, String userId, String name, LocalDateTime registrationDate){
        this.roomId = id;
        this.userId = userId;
        this.name = name;
        this.registrationDate = registrationDate;
//        measurements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRoomId(){
        return roomId;
    }

    public void onlyLastMeasurement(){
        Measurement measurement = this.getMeasurements().get(this.getMeasurements().size()-1);
        System.out.println(measurement.getId() + "last measurement");
        this.getMeasurements().clear();
        this.getMeasurements().add(measurement);
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}
