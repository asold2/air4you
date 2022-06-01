package SEP4Data.air4you;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import SEP4Data.air4you.measurement.Measurement;
import SEP4Data.air4you.measurement.MeasurementRepository;
import SEP4Data.air4you.measurement.MeasurementServiceImpl;
import SEP4Data.air4you.measurement.IMeasurementService;

@ExtendWith(MockitoExtension.class)
public class MeasurementServiceTest {

    @InjectMocks
    IMeasurementService measurementService = new MeasurementServiceImpl();

    @Mock
    MeasurementRepository dao;

    @Test
    public void findAllMeasurements(){

        List<Measurement> measurements = new ArrayList<>();
        Measurement measurementOne = new Measurement(new Date(),"roomId1",25,50,400);

        measurements.add(measurementOne);

        when(dao.findAll()).thenReturn(measurements);

        List<Measurement> measurementList = measurementService.getMeasurements("roomId1");

        assertEquals(1, measurementList.size());

        verify(dao, times(1)).findAll();

    }

//    @Test
//    public void addMeasurement(){
//
//        Measurement measurement = new Measurement(new Date(),"newRoomId",25,50,400);
//
//        verify(dao, times(0));
//
//        measurementService.addMeasurement(measurement);
//
//        verify(dao, times(1)).save(measurement);
//
//    }





}
