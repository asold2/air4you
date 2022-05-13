package SEP4Data.air4you.tempThreshold;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempThresholdRepository extends JpaRepository<TemperatureThreshold, Integer> {
}
