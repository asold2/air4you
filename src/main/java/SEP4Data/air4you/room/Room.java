package SEP4Data.air4you.room;

import SEP4Data.air4you.day.Day;
import SEP4Data.air4you.measurement.Measurement;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="room")
public class Room implements Serializable {
    @Id
    private String roomId;
    private String name;
    private String userId = "none";
    private LocalDateTime registrationDate;
    @OneToMany(mappedBy="roomId")
    private List<Day> days;
//    private String notoficationToken;





    public Room() {
    }

    public Room(String roomId, String userId, String name, LocalDateTime registrationDate){
        this.roomId = roomId;
        this.userId = userId;
        this.name = name;
        this.registrationDate = registrationDate;
        days = new ArrayList<Day>();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
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

//    public void onlyLastMeasurement(){
//        if(!this.getMeasurements().isEmpty()){
//            Measurement measurement = this.getMeasurements().get(this.getMeasurements().size()-1);
//            System.out.println(measurement.getId() + "last measurement");
//            this.getMeasurements().clear();
//            this.getMeasurements().add(measurement);
//        }
//    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }


    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", registrationDate=" + registrationDate +
                ", days=" + days +
                '}';
    }
}
