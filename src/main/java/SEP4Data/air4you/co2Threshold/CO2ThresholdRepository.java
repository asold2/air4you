package SEP4Data.air4you.co2Threshold;

import SEP4Data.air4you.humidityThreshold.HumidityThreshold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CO2ThresholdRepository extends JpaRepository<CO2Threshold, Integer>
{
}
