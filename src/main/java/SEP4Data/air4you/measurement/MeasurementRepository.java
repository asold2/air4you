package SEP4Data.air4you.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

//

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    @Modifying
    @Transactional
    void deleteMeasurementsByRoomId(String roomId);


    @Query(value = "Select AVG(temperature) from measurement where date between cast(current_date-:i as timestamp) and (cast(current_date+1-:i as timestamp)) and :roomId = room_id group by cast(current_date as timestamp)",nativeQuery = true)
    Double countAverageTemperature(@Param(value = "roomId") String roomId, @Param(value = "i") int i);

    @Query(value = "Select AVG(humidity) from measurement where date between cast(current_date-:i as timestamp) and (cast(current_date+1-:i as timestamp)) and :roomId = room_id group by cast(current_date as timestamp)",nativeQuery = true)
    Double countAverageHumidity(@Param(value = "roomId") String roomId, @Param(value = "i") int i);

    @Query(value = "Select AVG(co2) from measurement where date between cast(current_date-:i as timestamp) and (cast(current_date+1-:i as timestamp)) and :roomId = room_id group by cast(current_date as timestamp)",nativeQuery = true)
    Double countAverageCo2(@Param(value = "roomId") String roomId, @Param(value = "i") int i);

}
