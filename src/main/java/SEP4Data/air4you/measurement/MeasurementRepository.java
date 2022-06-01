package SEP4Data.air4you.measurement;

import SEP4Data.air4you.humidityThreshold.HumidityThreshold;
import SEP4Data.air4you.room.Room;
import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalTime;

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





    @Query(value = "SELECT ht FROM HumidityThreshold ht WHERE :measurementTime BETWEEN ht.startTime AND ht.endTime AND ht.roomId = :roomId")
    HumidityThreshold getCurrentHumidityThreshold(@Param(value = "measurementTime") LocalTime measurementTime, @Param(value = "roomId") String roomId);

    @Query(value = "SELECT tt FROM TemperatureThreshold tt WHERE :measurementTime BETWEEN tt.startTime AND tt.endTime AND tt.roomId = :roomId")
    TemperatureThreshold getCurrentTemperatureThreshold(@Param(value = "measurementTime") LocalTime measurementTime, @Param(value = "roomId") String roomId);





    @Query(value = "SELECT token FROM room INNER JOIN tokens on user_id = uid WHERE room_id = :roomId", nativeQuery = true)
    String getTokenFromRoomId(@Param(value = "roomId") String roomId);


    @Query(value = "SELECT name FROM room WHERE room_id = :roomId",
    nativeQuery = true)
    String getRoomName(@Param(value = "roomId") String roomId);
}
