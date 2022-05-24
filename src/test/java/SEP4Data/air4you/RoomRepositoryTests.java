package SEP4Data.air4you;

import SEP4Data.air4you.room.Room;
import SEP4Data.air4you.room.RoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@SpringBootTest
public class RoomRepositoryTests {

    @Test
    public void saveRoomTest(){
        Room room = Room.builder()
                .roomId("testRoomId")
                .userId("testUserId")
                .name("testRoomName")
                .registrationDate(LocalDateTime.now())
                .build();

    }

}

