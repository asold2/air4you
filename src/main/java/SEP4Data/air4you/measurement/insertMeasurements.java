package SEP4Data.air4you.measurement;

import SEP4Data.air4you.threshold.Threshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class insertMeasurements implements Runnable{

    @Autowired
    private IMeasurementService measurementService;

    public insertMeasurements(IMeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @Override
    public void run() {

        Date date = new Date(1652774400000L);

        Measurement measurement = new Measurement(date,"0004A30B00219CAC",50,50,600);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(measurement.getDate());

        for (int i = 0; i < 2000; i++) {
            calendar.add((Calendar.MINUTE),5);
            date.setTime(calendar.getTimeInMillis());
            measurement.setDate(date);
            measurementService.addMeasurement(
                    new Measurement(
                            measurement.getDate(),
                            measurement.getRoomId(),
                            measurement.getTemperature(),
                            measurement.getHumidity(),
                            measurement.getCo2()));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
