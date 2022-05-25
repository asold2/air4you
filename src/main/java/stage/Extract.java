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



    public void extractRoomToStage(){
        jdbcManager.execute("Insert into stage_air4you.room " +
            "select room_id, name, registration_date, user_id from room " +
            "except select room_id, name, registration_date, user_id " +
            "from stage_air4you.room");
    }

    public void stageDimHumidityThresholdCreation(){
        jdbcManager.execute("CREATE TABLE IF NOT EXISTS stage_air4you.DimHumidityThreshold ("+
            "humidityThresholdId VARCHAR(255) not null primary key,"+
            "roomId VARCHAR(255),"+
            "startTime timestamp,"+
            "endTime timestamp,"+
            "minimumValue DECIMAL(5,2),"+
            "maximumValue DECIMAL(5,2)," +
                "FOREIGN KEY (roomId) REFERENCES stage_air4you.DimRoom (room_id)"+
            ")");
    }

    public void extractDimHumidityThresholdToStage()
    {
        jdbcManager.execute("Insert into stage_air4you.DimHumidityThreshold ( " +
            "humidityThresholdId, roomId, startTime, endTime, minimumValue, maximumValue from DImHumidityThreshold "+
            "except select humidityThresholdId, roomId, startTime, endTime, minimumValue, maximumValue "+
            "from stage_air4you.DimHumidityThreshold) "
            );
    }

    public void extractDimUserToStage(){
        jdbcManager.execute("insert into stage_air4you.DimUser" +
                "select stage_air4you.DimRoom.user_id");
    }








}
