package SEP4Data.air4you.room;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="room")
public class Room {
    @Id
    private int roomId;
    private int area;

    public Room() {
    }

    public Room(int id){
        this.roomId = id;
    }

    public int getRoomId(){
        return roomId;
    }

}
