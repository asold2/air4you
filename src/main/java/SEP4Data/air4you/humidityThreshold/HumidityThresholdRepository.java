package SEP4Data.air4you.humidityThreshold;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.time.LocalTime;

@Repository
public interface HumidityThresholdRepository extends JpaRepository<HumidityThreshold, Integer>
{
  @Modifying
  @Transactional
  @Query(value = "update HumidityThreshold t set t.max = :max, t.min = :min, t.startTime = :startTime, t.endTime = :endTime where t.Id = :Id")
  void updateHumidityThreshold(@Param(value = "max") double max, @Param(value = "min") double min,@Param(value = "startTime") LocalTime startTime,@Param(value = "endTime") LocalTime endTime, @Param(value = "Id") int Id);


}