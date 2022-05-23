package SEP4Data.air4you.tempThreshold;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TempThresholdRepository extends JpaRepository<TemperatureThreshold, Integer> {
    @Modifying
    @Transactional
    @Query(value = "update TemperatureThreshold t set t.max = :max, t.min = :min where t.Id = :Id ")
    void updateTempThreshold(@Param(value = "max") double max, @Param(value = "min") double min, @Param(value = "Id") int id);

}
