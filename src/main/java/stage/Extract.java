package stage;

public class Extract {
    private JDBCManager jdbcManager = null;

    public Extract(){
        jdbcManager = JDBCManager.getInstance();
        stageDimRoomCreation();
        extractRoomToStage();
//        stageDimUserCreation();
//        stageDimMeasurementCreation();
//        System.out.println("Somthing");
//        stageDimHumidityThresholdCreation();
//        extractDimHumidityThresholdToStage();
//        stageDimTokenCreation();
//        extractTokenToStage();
        extractDimUserToStage();
        stageDimHumidityThresholdCreation();
    }

    public void stageDimRoomCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.Dim_Room (" +
                "room_id VARCHAR(255) not null primary key," +
                "name VARCHAR(255)," +
                "registration_date timestamp," +
                "user_id varchar(255) " +
                ")");

    }
    public void stageDimTokenCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.Dim_Token(" +
                "id integer not null primary key," +
                "uid varchar(255)," +
                "token varchar(255)" +
                ")");
    }
    public void stageDimUserCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.Dim_User(" +
                "user_Id varchar(255) not null primary key," +
                "token varchar(255)," +
                "email varchar(255)," +
                "name varchar(255)" +
                ")");
    }



    public void stageDimMeasurementCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.Dim_Measurement (" +
                "measurement_Id VARCHAR(255) not null primary key," +
                "room_Id VARCHAR(255)," +
                "date timestamp," +
                "temperature DECIMAL(5,2)," +
                "humidity DECIMAL(5,2)," +
                "co2 DECIMAL(5,2) ," +
                "temperature_Exceeded BOOLEAN," +
                "humidity_Exceeded BOOLEAN," +
                "co2_Exceeded BOOLEAN," +
                "FOREIGN KEY (room_Id) REFERENCES stage_air4you.Dim_Room (room_Id)"+
                ")");
    }

    public void extractDimMeasurementToStage(){
        jdbcManager.execute("Insert into stage_air4you.DimMeasurement " +
                "select measurement_Id, room_Id, date, temperature, humidity, co2, temperature_Exceeded, humidity_Exceeded, co2_Exceeded from measurement" +
                "except measurement_Id, room_Id, date, temperature, humidity, co2, temperature_Exceeded, humidity_Exceeded, co2_Exceeded" +
                "from stage_air4you.DimMeasurement");
    }

    public void extractRoomToStage(){
        jdbcManager.execute("Insert into stage_air4you.Dim_Room " +
            "select room_id, name, registration_date, user_id from room " +
                "except select room_id, name, registration_date, user_id from stage_air4you.Dim_Room  "

        );
    }
    public void extractTokenToStage(){
        jdbcManager.execute("insert into stage_air4you.dim_token " +
                "select id, uid, token from tokens " +
                "except select id, uid, token from stage_air4you.dim_token "
        );
    }

    public void stageDimTemperatureThresholdCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.dim_temperature_threshold(" +
                "temperature_threshold_id INT not null primary key," +
                "room_id VARCHAR(255)," +
                "minimum_value DECIMAL(4,2)," +
                "maximum_value DECIMAL(4,2)," +
                "start_time TIME(10)," +
                "end_time TIME(10)," +
                "FOREIGN KEY (room_id) REFERENCES stage_air4you.DimRoom (room_id)" +
                ")");
    }

    public void extractTemperatureThresholdToStage(){
        jdbcManager.execute("Insert into stage_air4you.dim_temperature_threshold (" +
                "select temperature_threshold_id,room_id, minimum_value,maximum_value,start_time,end_time from stage_air4you.dim_temperature_threshold " +
                "except select id,room_id, min,max,start_time,end_time " +
                "from public.temperature_thresholds) ");
    }

    public void stageDimHumidityThresholdCreation(){
        jdbcManager.execute("CREATE TABLE IF NOT EXISTS stage_air4you.Dim_HumidityThreshold ("+
            "humidity_threshold_id VARCHAR(255) not null primary key,"+
            "room_id VARCHAR(255),"+
            "start_time timestamp,"+
            "end_time timestamp,"+
            "minimum_value DECIMAL(5,2),"+
            "maximum_value DECIMAL(5,2)," +
                "FOREIGN KEY (room_id) REFERENCES stage_air4you.DimRoom (room_id)"+
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

    //Need to take data from Dim_Room and Dim_Token from stage.
    public void extractDimUserToStage(){
        jdbcManager.execute("insert into stage_air4you.Dim_User\n" +
                "                select stage_air4you.Dim_Room.user_id, stage_air4you.dim_token.token\n" +
                "                from stage_air4you.Dim_Room\n" +
                "                inner join stage_air4you.Dim_Token\n" +
                "            on stage_air4you.Dim_Room.user_id = stage_air4you.Dim_Token.uid " +
                "except select user_id, token from stage_air4you.Dim_User");
    }








}
