package SEP4Data.air4you.room;

import SEP4Data.air4you.measurement.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    @Transactional
    @Modifying
    @Query(value="update Room room set last_Measurement = :measurement where room.room_id = :roomId", nativeQuery = true)
    void updateRoomMeasurement(@Param("measurement")Measurement measurement, @Param("roomId")String roomId);

    @Modifying
    @Transactional
    @Query(value = "update Room r set r.name = :name where r.roomId = :roomId AND r.userId = :userId")
    void updateRoomName(@Param(value = "name") String name, @Param("roomId") String roomId, @Param("userId") String userId);

}
