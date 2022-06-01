package stage;

public class ETLProcess {

    JDBCManager jdbcManager;
    public ETLProcess(){
        jdbcManager = JDBCManager.getInstance();
        // Create Stage schema
        createStageSchema();
        // STAGING - Creating all staging tables for the dimension and fact tables
        setupDimensionsAndFacts();
        // Continue to extraction
        Extract extract = new Extract();
    }

    private void createStageSchema(){
        jdbcManager.execute("CREATE SCHEMA IF NOT EXISTS stage_air4you");
    }
    private void setupDimensionsAndFacts(){
        stageDimRoomCreation();
        stageDimUserCreation();
        stageDimMeasurementCreation();
        stageDimTokenCreation();
        stageDimDateCreation();
        stageRegistrationFactCreation();
        stageFactMeasurementCreation();
        addValidToandValidFromToStageDimensions();


    }




    private void stageDimRoomCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.Dim_Room ( " +
                "R_Id serial  primary key, " +
                "room_id VARCHAR(255) not null," +
                "name VARCHAR(255)," +
                "registration_date timestamp," +
                "user_id varchar(255) " +
                ")");
    }
    private void stageDimTokenCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.Dim_Token( "+
                "id integer not null primary key," +
                "uid varchar(255)," +
                "token varchar(255)" +
                ")");
    }
    private void stageDimUserCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.Dim_User( " +
                "U_Id serial not null primary key, " +
                "user_Id varchar(255) not null ," +
                "token varchar(255)," +
                "email varchar(255)," +
                "name varchar(255)" +
                ")");
    }


    private void stageDimMeasurementCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.Dim_Measurement ( " +
                "measurement_Id INT not null primary key," +
                "room_Id VARCHAR(255)," +
                "date timestamp," +
                "temperature DECIMAL (6,2)," +
                "humidity DECIMAL (6,2)," +
                "co2 DECIMAL (6,2) ," +
                "temperature_Exceeded BOOLEAN," +
                "humidity_Exceeded BOOLEAN," +
                "co2_Exceeded BOOLEAN" +
                ")");
    }


    private void stageDimDateCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.dim_date (\n" +
                "    date_id serial not null primary key,\n" +
                "    year int,\n" +
                "    month int,\n" +
                "    week int,\n" +
                "    day int,\n" +
                "    hour int,\n" +
                "    minute int,\n" +
                "    fact_type varchar(40)" +
                ")");
    }

    private void stageRegistrationFactCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.fact_registration(\n" +
                "    user_id int not null,\n" +
                "    room_id int not null,\n" +
                "    date_id  int not null,\n" +
                "    foreign key (user_id) references stage_air4you.dim_user (u_id),\n" +
                "    foreign key (room_id) references stage_air4you.dim_room (r_id),\n" +
                "    foreign key (date_id) references stage_air4you.dim_date (date_id),\n" +
                "    constraint Registration_PK PRIMARY KEY(user_id, room_id, date_id)\n" +
                "    );");
    }
    private void stageFactMeasurementCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.fact_measurement(\n" +
                "                                                   measurement_id int not null,\n" +
                "                                                    user_id int not null,\n" +
                "                                                    room_id int not null,\n" +
                "                                                    date_id  int not null,\n" +
                "                                                    foreign key (user_id) references stage_air4you.dim_user (u_id) on delete cascade,\n" +
                "                                                    foreign key (room_id) references stage_air4you.dim_room (r_id) on delete cascade,\n" +
                "                                                    foreign key (date_id) references stage_air4you.dim_date (date_id) on delete cascade,\n" +
                "                                                    foreign key (measurement_id) references stage_air4you.dim_measurement (measurement_id) on delete cascade,\n" +
                "                                                    constraint fact_measurement_PK PRIMARY KEY(user_id, room_id, date_id, measurement_id)\n" +
                "                                                    )");
    }

    private void addValidToandValidFromToStageDimensions() {
        jdbcManager.execute("alter table stage_air4you.dim_measurement\n" +
                "add if not exists ValidFrom int, add if not exists ValidTo int;\n" +
                "alter table stage_air4you.dim_room\n" +
                "add if not exists ValidFrom int, add if not exists ValidTo int;\n" +
                "alter table stage_air4you.dim_date\n" +
                "add  if not exists ValidFrom int,  add if not exists ValidTo int;\n" +
                "alter table stage_air4you.dim_token\n" +
                "add if not exists ValidFrom int,  add if not exists ValidTo int;\n" +
                "alter table stage_air4you.dim_user\n" +
                "add if not exists ValidFrom int, add if not exists ValidTo int\n" +
                ";\n");

    }
}
