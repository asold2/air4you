package SEP4Data.air4you.measurement;

import SEP4Data.air4you.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

//

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    @Modifying
    @Transactional
    void deleteMeasurementsByRoomId(String roomId);

}
