package SEP4Data.air4you.room;

import SEP4Data.air4you.measurement.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
@Entity
@Component
@Table(name="room")
public class Room implements Serializable {
    @Id
    private String roomId;
    private String name;
    private String userId = "none";
    private LocalDateTime registrationDate;
    @OneToMany(mappedBy="roomId")
    private List<Measurement> measurements;
//    private String notoficationToken;


    public Room() {
    }

    public Room(String roomId, String userId, String name, LocalDateTime registrationDate){
        this.roomId = roomId;
        this.userId = userId;
        this.name = name;
        this.registrationDate = registrationDate;
        measurements = new ArrayList<Measurement>();
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
        if(!this.getMeasurements().isEmpty()){
            Measurement measurement = this.getMeasurements().get(this.getMeasurements().size()-1);
            System.out.println(measurement.getId() + "last measurement");
            this.getMeasurements().clear();
            this.getMeasurements().add(measurement);
        }
    }

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
                ", measurements=" + measurements +
                '}';
    }
}
