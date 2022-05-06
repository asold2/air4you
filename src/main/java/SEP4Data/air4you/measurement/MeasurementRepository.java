package SEP4Data.air4you.measurement;

import SEP4Data.air4you.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

//

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
