package SEP4Data.air4you.humidityThreshold;

import SEP4Data.air4you.tempThreshold.TemperatureThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumidityThresholdRepository extends JpaRepository<HumidityThreshold, Integer>
{
}
