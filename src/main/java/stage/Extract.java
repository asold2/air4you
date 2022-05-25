package stage;

public class Extract {
    private JDBCManager jdbcManager = null;

    public Extract(){
        jdbcManager = JDBCManager.getInstance();
        stageDimRoomCreation();
        stageDimUserCreation();
        stageDimHumidityThresholdCreation();
        extractDimHumidityThresholdToStage();
    }

    public void stageDimRoomCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.DimRoom (" +
                "room_id VARCHAR(255) not null primary key," +
                "name VARCHAR(255)," +
                "registration_date timestamp," +
                "user_id varchar(255) " +
                ")");

    }


    public void stageDimUserCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.DimUser(" +
                "userId varchar(255) not null primary key," +
                "token varchar(255)," +
                "email varchar(255)," +
                "name varchar(255)" +
                ")");
    }



    public void stageDimMeasurementCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.DimMeasurement (" +
                "measurement_Id VARCHAR(255) not null primary key," +
                "room_Id VARCHAR(255) FOREIGN KEY REFERENCES stage_air4you.DimRoom(roomId)," +
                "date timestamp," +
                "temperature DECIMAL(5,2)," +
                "humidity DECIMAL(5,2)," +
                "co2 DECIMAL(5,2) ," +
                "temperature_Exceeded BOOLEAN," +
                "humidity_Exceeded BOOLEAN," +
                "co2_Exceeded BOOLEAN," +
                "FOREIGN KEY (room_Id) REFERENCES stage_air4you.DimRoom (room_Id)"+
                ")");
    }

    public void extractDimMeasurementToStage(){
        jdbcManager.execute("Insert into stage_air4you.DimMeasurement " +
                "select measurement_Id, room_Id, date, temperature, humidity, co2, temperature_Exceeded, humidity_Exceeded, co2_Exceeded from measurement" +
                "except measurement_Id, room_Id, date, temperature, humidity, co2, temperature_Exceeded, humidity_Exceeded, co2_Exceeded" +
                "from stage_air4you.DimMeasurement");
    }

    public void extractRoomToStage(){
        jdbcManager.execute("Insert into stage_air4you.room " +
            "select room_id, name, registration_date, user_id from room " +
            "except select room_id, name, registration_date, user_id " +
            "from stage_air4you.room");
    }

    public void stageDimTemperatureThresholdCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.DimTemperatureThreshold(" +
                "temperatureThresholdId VARCHAR(255) not null primary key," +
                "roomId VARCHAR(255)," +
                "minValue DECIMAL(4,2)," +
                "maxValue DECIMAL(4,2)," +
                "startTime TIME(10)," +
                "endTIme TIME(10)," +
                "FOREIGN KEY (roomId) REFERENCES stage_air4you.DimRoom (room_id)" +
                ")");
    }

    public void extractTemperatureThresholdToStage(){
        jdbcManager.execute("Insert into stage_air4you.DimTemperatureThreshold (" +
                "select temperatureThresholdId,roomId, minValue,maxValue,startTime,endTime from DimTemperatureThreshold " +
                "except select id,room_id, min,max,start_time,end_time " +
                "from stage_air4you.temperature_thresholds) ");
    }

    public void stageDimHumidityThresholdCreation(){
        jdbcManager.execute("CREATE TABLE IF NOT EXISTS stage_air4you.Dim_HumidityThreshold ("+
            "humidity_threshold_id VARCHAR(255) not null primary key,"+
            "room_id VARCHAR(255),"+
            "start_time timestamp,"+
            "end_time timestamp,"+
            "minimum_value DECIMAL(5,2),"+
            "maximum_value DECIMAL(5,2)," +
                "FOREIGN KEY (room_id) REFERENCES stage_air4you.Dim_Room (room_id)"+
            ")");
    }

    public void extractDimHumidityThresholdToStage()
    {
        jdbcManager.execute("Insert into stage_air4you.Dim_HumidityThreshold ( " +
            "humidity_threshold_id, room_id, start_time, end_time, minimum_value, maximum_value from Dim_HumidityThreshold "+
            "except select humidity_threshold_id, room_id, start_time, end_time, minimum_value, maximum_value "+
            "from stage_air4you.Dim_HumidityThreshold) "
            );
    }

    public void extractDimUserToStage(){
        jdbcManager.execute("insert into stage_air4you.DimUser" +
                "select stage_air4you.DimRoom.user_id");
    }








}
