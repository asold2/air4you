package SEP4Data.air4you.humidityThreshold;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HumidityThresholdRowMapper implements RowMapper<HumidityThreshold> {
    @Override
    public HumidityThreshold mapRow(ResultSet rs, int rowNum) throws SQLException {
        HumidityThreshold humidityThreshold = new HumidityThreshold();

        humidityThreshold.setRoomId(rs.getString("room_id"));
        humidityThreshold.setStartTime(rs.getTime("start_time").toLocalTime());
        humidityThreshold.setEndTime(rs.getTime("end_time").toLocalTime());
        humidityThreshold.setMin(rs.getDouble("min"));
        humidityThreshold.setMax(rs.getDouble("max"));

        return humidityThreshold;

    }
}
