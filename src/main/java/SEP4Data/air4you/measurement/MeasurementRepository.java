package SEP4Data.air4you.measurement;

import SEP4Data.air4you.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}