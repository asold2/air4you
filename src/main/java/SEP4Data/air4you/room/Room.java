package SEP4Data.air4you.room;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="room")
public class Room {
    @Id
    private int roomId;
    private String userId = "none";
    private LocalDateTime registrationDate;

    public Room() {
    }

    public Room(int id, String userId, LocalDateTime registrationDate){
        this.roomId = id;
        this.userId = userId;
        this.registrationDate = registrationDate;
    }

    public void setRoomId(int roomId) {
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

    public int getRoomId(){
        return roomId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}
