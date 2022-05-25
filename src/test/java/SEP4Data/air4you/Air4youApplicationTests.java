package SEP4Data.air4you;

import SEP4Data.air4you.room.RoomController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public
class Air4youApplicationTests {

	// CHECKING CONTROLLERS IS NOT NULL
	@Autowired
	private RoomController roomController;

	@Test
	void contextLoads() {
		assertThat(roomController).isNotNull();
	}

}
