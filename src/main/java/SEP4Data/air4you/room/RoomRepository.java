package SEP4Data.air4you.room;

import SEP4Data.air4you.measurement.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    @Transactional
    @Modifying
    @Query(value="update Room room set last_Measurement = :measurement where room.room_id = :roomId", nativeQuery = true)
    void updateRoomMeasurement(@Param("measurement")Measurement measurement, @Param("roomId")String roomId);


}
