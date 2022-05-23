package SEP4Data.air4you.room;

import SEP4Data.air4you.day.Day;
import SEP4Data.air4you.measurement.Measurement;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room implements Serializable {
    @Id
    private String id;
    private String name;
    private String userId = "none";
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "room")
    private List<Day> days;

    public Room() {
    }

    public Room(String id, String userId, String name){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.registrationDate = LocalDateTime.now();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String roomId) {
        this.id = roomId;
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

    public String getId(){
        return id;
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

}
