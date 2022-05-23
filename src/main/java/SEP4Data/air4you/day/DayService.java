package SEP4Data.air4you.day;

import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public interface DayService {

    void create(Day day);
    Day read(String id);

    Day getDayByDate(Date date);

    Day update(Day day);
    void delete(Day day);

}
