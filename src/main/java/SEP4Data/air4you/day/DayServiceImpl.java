package SEP4Data.air4you.day;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;

@Service
public class DayServiceImpl implements DayService {

    @Autowired
    DayRepository dayRepository;


    @Override
    public void create(Day day) {
        dayRepository.save(day);
    }

    @Override
    public Day read(String id) {
        return dayRepository.getById(id);
    }

    @Override
    public Day getDayByDate(Date date) {

        Calendar measurementCalendar = Calendar.getInstance();
        measurementCalendar.setTime(date);

        int measurementYear = measurementCalendar.get(Calendar.YEAR);
        int measurementMonth = measurementCalendar.get(Calendar.MONTH);
        int measurementDay = measurementCalendar.get(Calendar.DATE);

        for (Day day:
                dayRepository.findAll()) {

            Calendar dayCalender = Calendar.getInstance();
            dayCalender.setTime(day.getDayDate());

            int dayYear = dayCalender.get(Calendar.YEAR);
            int dayMonth = dayCalender.get(Calendar.MONTH);
            int dayDay = dayCalender.get(Calendar.DATE);

            if(measurementYear == dayYear && measurementMonth == dayMonth && measurementDay == dayDay){
                return day;
            }
        }
        return null;
    }


    @Override
    public Day update(Day day) {
        return null;
    }

    @Override
    public void delete(Day day) {

    }
}
