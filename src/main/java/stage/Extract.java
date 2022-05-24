package stage;

public class Extract {
    private JDBCManager jdbcManager = null;

    public Extract(){
        jdbcManager = JDBCManager.getInstance();
//        stageSchemaCreation();
    }

    public void stageSchemaCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.room (" +
                "room_id VARCHAR(255) not null primary key," +
                "name VARCHAR(255)," +
                "registration_date timestamp," +
                "user_id varchar(255) " +
                ")");

    }

    public void extractRoomToStage(){
        jdbcManager.execute("Insert into stage_air4you.room " +
                "select room_id, name, registration_date, user_id from room " +
                "except select room_id, name, registration_date, user_id " +
                "from stage_air4you.room");
    }


}
