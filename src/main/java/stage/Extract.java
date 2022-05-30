package stage;

import org.springframework.dao.DuplicateKeyException;

public class Extract {
    private JDBCManager jdbcManager = null;
    private Transform transform = null;

    public Extract(){
        jdbcManager = JDBCManager.getInstance();

        createStageSchema();

        stageDimRoomCreation();
        stageDimUserCreation();
        stageDimMeasurementCreation();
//        stageDimHumidityThresholdCreation();
//        stageDimTemperatureThresholdCreation();
        stageDimTokenCreation();
        stageDimDateCreation();
        stageRegistrationFactCreation();
        stageFactMeasurementCreation();


        extractRoomToStage();
        extractDimMeasurementToStage();
        extractTokenToStage();
        extractDimUserToStage();

        extractDimDateFromRoomToStage();
        extractDimDateFromMeasurementToStage();

        extractToFactRegistrationStage();
        extractFactMeasurementToStage();

        transform = new Transform();

    }

    private void createStageSchema(){
        jdbcManager.execute("CREATE SCHEMA IF NOT EXISTS stage_air4you");
    }

    private void extractFactMeasurementToStage() {
        jdbcManager.execute(
                "insert into stage_air4you.fact_measurement(" +
                        " measurement_id," +
                        " user_id," +
                        " room_id," +
                        " date_id" +
                        ")select" +
                        " dim_measurement.measurement_id," +
                        " dim_user.u_id," +
                        " dim_room.r_id," +
                        " dim_date.date_id" +
                        " from " +
                        " stage_air4you.dim_date, " +
                        " stage_air4you.dim_room" +
                        " inner join stage_air4you.dim_user on dim_room.user_id = dim_user.user_id" +
                        " inner join stage_air4you.dim_measurement on dim_measurement.room_id = dim_room.room_id" +
                        " where  fact_type = 'measurement' and (EXTRACT(YEAR FROM dim_measurement.date) = dim_date.year ) and (EXTRACT(MONTH FROM dim_measurement.date) = dim_date.month )" +
                        " and (EXTRACT(DAY FROM dim_measurement.date) = dim_date.day ) and (EXTRACT(HOUR FROM dim_measurement.date) = dim_date.hour)" +
                        " and (EXTRACT(MINUTE FROM dim_measurement.date) = dim_date.minute )" +
                        "-- and (dim_measurement.humidity_exceeded = true or dim_measurement.temperature_exceeded = true or dim_measurement.co2_exceeded = true)\n" +
                        " on conflict do nothing" +
                        ";");
    }

    private void extractToFactRegistrationStage() {
        try{
            jdbcManager.execute("\n" +
                    "insert into stage_air4you.fact_registration(\n" +
                    "    user_id, room_id, date_id\n" +
                    ") SELECT dim_user.u_id, dim_room.r_id, dim_date.date_id\n" +
                    "         from stage_air4you.dim_date, stage_air4you.dim_user inner join stage_air4you.dim_room\n" +
                    "on dim_room.user_id = dim_user.user_id\n" +
                    "where fact_type = 'room_registartion' and (EXTRACT(YEAR FROM dim_room.registration_date) = dim_date.year ) and (EXTRACT(MONTH FROM dim_room.registration_date) = dim_date.month )\n" +
                    "and (EXTRACT(DAY FROM dim_room.registration_date) = dim_date.day ) and (EXTRACT(HOUR FROM dim_room.registration_date) = dim_date.hour)\n" +
                    "  and (EXTRACT(MINUTE FROM dim_room.registration_date) = dim_date.minute )\n" +
                    "on conflict do nothing\n" +
                    ";\n");
        }catch(DuplicateKeyException e){
            throw e;
        }
    }


    public void stageDimRoomCreation(){
//        jdbcManager.execute("drop table stage_air4you.Dim_Room cascade ");
        jdbcManager.execute("create table if not exists stage_air4you.Dim_Room ( " +
                "R_Id serial  primary key, " +
                "room_id VARCHAR(255) not null," +
                "name VARCHAR(255)," +
                "registration_date timestamp," +
                "user_id varchar(255) " +
                 ")");

    }
    public void stageDimTokenCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.Dim_Token( "+
                "id integer not null primary key," +
                "uid varchar(255)," +
                "token varchar(255)" +
                ")");
    }
    public void stageDimUserCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.Dim_User( " +
                "U_Id serial not null primary key, " +
                "user_Id varchar(255) not null ," +
                "token varchar(255)," +
                "email varchar(255)," +
                "name varchar(255)" +
                ")");
    }
    public void stageDimTemperatureThresholdCreation(){
        jdbcManager.execute("create table if not exists stage_air4you.dim_temperature_threshold(" +
                "temperature_threshold_id INT not null primary key," +
                "room_id VARCHAR(255)," +
                "minimum_value DECIMAL(5,2)," +
                "maximum_value DECIMAL(5,2)," +
                "start_time TIME(10)," +
                "end_time TIME(10)" +
                ")");
    }

    public void stageDimHumidityThresholdCreation(){
        jdbcManager.execute("CREATE TABLE IF NOT EXISTS stage_air4you.Dim_HumidityThreshold ("+
                "humidity_threshold_id int not null primary key,"+
                "room_id VARCHAR(255),"+
                "start_time TIME(10),"+
                "end_time TIME(10),"+
                "minimum_value DECIMAL (5,2),"+
                "maximum_value DECIMAL (5,2)" +
                ")");
    }

    public void stageDimMeasurementCreation(){
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

//    public void stageDimDateCreation(){
//        jdbcManager.execute("create table if not exists stage_air4you.dim_date (\n" +
//                "    date_id serial not null primary key,\n" +
//                "    year varchar(10),\n" +
//                "    month varchar (20),\n" +
//                "    week varchar(20),\n" +
//                "    day varchar (20),\n" +
//                "    hour varchar(20),\n" +
//                "    minute varchar(20),\n" +
//                "    fact_type varchar(40)" +
//                ")");
//    }

    public void stageDimDateCreation(){
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

    public void stageRegistrationFactCreation(){
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
    public void stageFactMeasurementCreation(){
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

    public void extractDimMeasurementToStage(){
        jdbcManager.execute("insert into stage_air4you.dim_measurement\n" +
                "(measurement_id, room_id, date, temperature, humidity, co2, temperature_exceeded, humidity_exceeded, co2_exceeded) SELECT\n" +
                "id,room_id,measurement.date,temperature,humidity, co2,temperature_exceeded, humidity_exceeded,co2exceeded from measurement\n" +
                "except select measurement_id, room_id, date, temperature, humidity, co2, temperature_exceeded, humidity_exceeded, co2_exceeded from stage_air4you.dim_measurement");
//        jdbcManager.execute("update stage_air4you.dim_measurement\n" +
//                "set r_id = dim_room.r_id\n" +
//                "from stage_air4you.dim_room\n" +
//                "where\n" +
//                " stage_air4you.dim_measurement.room_id = stage_air4you.dim_room.room_id");
    }

    public void extractRoomToStage(){
        jdbcManager.execute("Insert into stage_air4you.Dim_Room(\n" +
                "                                   room_id, name, registration_date, user_id\n" +
                ") SELECT room.room_id, room.name, room.registration_date, room.user_id from room\n" +
                "                except select room_id, name, registration_date, user_id from stage_air4you.Dim_Room"

        );
    }
    public void extractTokenToStage(){
        jdbcManager.execute("insert into stage_air4you.dim_token " +
                "select id, uid, token from tokens " +
                "except select id, uid, token from stage_air4you.dim_token "
        );
    }



    public void extractTemperatureThresholdToStage(){
        jdbcManager.execute("Insert into stage_air4you.dim_temperature_threshold (\n" +
                "                select id,room_id, min,max,start_time,end_time\n" +
                "                from temperature_thresholds\n" +
                "                except select temperature_threshold_id,room_id, minimum_value,maximum_value,start_time,end_time\n" +
                "                from stage_air4you.dim_temperature_threshold)");
    }



    public void extractDimHumidityThresholdToStage()
    {
        jdbcManager.execute("Insert into stage_air4you.Dim_HumidityThreshold(\n" +
                "                                                humidity_threshold_id, room_id, start_time, end_time, minimum_value, maximum_value\n" +
                ") SELECT\n" +
                "            id, room_id, end_time, start_time, max, min\n" +
                "            from humidity_thresholds\n" +
                "            except select humidity_threshold_id, room_id, start_time, end_time, minimum_value, maximum_value\n" +
                "            from stage_air4you.Dim_HumidityThreshold"
            );
    }

    //Need to take data from Dim_Room and Dim_Token from stage.
    public void extractDimUserToStage(){
        jdbcManager.execute("insert into stage_air4you.Dim_User\n" +
                "(user_id, token )" +
                "                select stage_air4you.Dim_Room.user_id, stage_air4you.dim_token.token\n" +
                "                from stage_air4you.Dim_Room\n" +
                "                inner join stage_air4you.Dim_Token\n" +
                "            on stage_air4you.Dim_Room.user_id = stage_air4you.Dim_Token.uid " +
                "except select user_id, token from stage_air4you.Dim_User");
    }

    public void extractDimDateFromRoomToStage(){
        jdbcManager.execute("\n" +
                "insert into stage_air4you.dim_date(\n" +
                " year, month, week, day, hour, minute, fact_type\n" +
                ") select\n" +
                "date_part('year', registration_date),\n" +
                "date_part('month', registration_date),\n" +
                "date_part('week', registration_date),\n" +
                "date_part('day', registration_date),\n" +
                "date_part('hour', registration_date),\n" +
                "date_part('minute', registration_date),\n" +
                "         'room_registartion'\n" +
                "from stage_air4you.dim_room\n" +
                "except select cast(year as double precision), cast(month as double precision),\n" +
                "              cast(week as double precision),\n" +
                "              cast(day as double precision),\n" +
                "              cast(hour as double precision),\n" +
                "              cast(minute as double precision),\n" +
                "              fact_type\n" +
                "from stage_air4you.dim_date");
    }

    public void extractDimDateFromMeasurementToStage(){
        jdbcManager.execute("insert into stage_air4you.dim_date(\n" +
                " year, month, week, day, hour, minute, fact_type\n" +
                ") select\n" +
                "--     extract(registration_date.date) as year\n" +
                "date_part('year', date),\n" +
                "date_part('month', date),\n" +
                "date_part('week', date),\n" +
                "date_part('day', date),\n" +
                "date_part('hour', date),\n" +
                "date_part('minute', date),\n" +
                "'measurement'\n" +
                "from stage_air4you.dim_measurement\n" +
                "except select cast(year as double precision), cast(month as double precision),\n" +
                "              cast(week as double precision),\n" +
                "              cast(day as double precision),\n" +
                "              cast(hour as double precision),\n" +
                "              cast(minute as double precision),\n" +
                "              fact_type\n" +
                "              from stage_air4you.dim_date");
    }





















}
