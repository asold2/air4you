//package stage;
//
//import org.springframework.dao.DuplicateKeyException;
//
//public class Extract {
//    private JDBCManager jdbcManager;
//    private Transform transform;
//
///*Within the constructor all the methods implemented in this class are called, to first
//* create the staging tables, then to populate it with data from the source system*/
//    public Extract(){
//        jdbcManager = JDBCManager.getInstance();
//
//        extract();
//
//         transform = new Transform();
//
//    }
//
//    private void extract(){
//        extractRoomToStage();
//        extractDimMeasurementToStage();
//        extractTokenToStage();
//        extractDimUserToStage();
//
//        extractDimDateFromRoomToStage();
//        extractDimDateFromMeasurementToStage();
//
//        insertValidToAndValidFromToDimensionsStage();
//
//
//        extractToFactRegistrationStage();
//        extractFactMeasurementToStage();
//    }
//
//    private void extractFactMeasurementToStage() {
//        jdbcManager.execute(
//                "insert into stage_air4you.fact_measurement(" +
//                        " measurement_id," +
//                        " user_id," +
//                        " room_id," +
//                        " date_id" +
//                        ")select" +
//                        " dim_measurement.measurement_id," +
//                        " dim_user.u_id," +
//                        " dim_room.r_id," +
//                        " dim_date.date_id" +
//                        " from " +
//                        " stage_air4you.dim_date, " +
//                        " stage_air4you.dim_room" +
//                        " inner join stage_air4you.dim_user on dim_room.user_id = dim_user.user_id" +
//                        " inner join stage_air4you.dim_measurement on dim_measurement.room_id = dim_room.room_id" +
//                        " where  fact_type = 'measurement' and (EXTRACT(YEAR FROM dim_measurement.date) = dim_date.year ) and (EXTRACT(MONTH FROM dim_measurement.date) = dim_date.month )" +
//                        " and (EXTRACT(DAY FROM dim_measurement.date) = dim_date.day ) and (EXTRACT(HOUR FROM dim_measurement.date) = dim_date.hour)" +
//                        " and (EXTRACT(MINUTE FROM dim_measurement.date) = dim_date.minute )" +
//                        "-- and (dim_measurement.humidity_exceeded = true or dim_measurement.temperature_exceeded = true or dim_measurement.co2_exceeded = true)\n" +
//                        " on conflict do nothing" +
//                        ";");
//    }
//
//    private void extractToFactRegistrationStage() {
//        try{
//            jdbcManager.execute("\n" +
//                    "insert into stage_air4you.fact_registration(\n" +
//                    "    user_id, room_id, date_id\n" +
//                    ") SELECT dim_user.u_id, dim_room.r_id, dim_date.date_id\n" +
//                    "         from stage_air4you.dim_date, stage_air4you.dim_user inner join stage_air4you.dim_room\n" +
//                    "on dim_room.user_id = dim_user.user_id\n" +
//                    "where fact_type = 'room_registartion' and (EXTRACT(YEAR FROM dim_room.registration_date) = dim_date.year ) and (EXTRACT(MONTH FROM dim_room.registration_date) = dim_date.month )\n" +
//                    "and (EXTRACT(DAY FROM dim_room.registration_date) = dim_date.day ) and (EXTRACT(HOUR FROM dim_room.registration_date) = dim_date.hour)\n" +
//                    "  and (EXTRACT(MINUTE FROM dim_room.registration_date) = dim_date.minute )\n" +
//                    "on conflict do nothing\n" +
//                    ";\n");
//        }catch(DuplicateKeyException e){
//            throw e;
//        }
//    }
//
///*STAGING - Creating all staging tables for the dimension and fact tables*/
//
//
//    /*EXTRACTING - Extracting data from fact and dimensional data, and inserting it to the staging tables*/
//    public void extractDimMeasurementToStage(){
//        jdbcManager.execute("insert into stage_air4you.dim_measurement\n" +
//                "(measurement_id, room_id, date, temperature, humidity, co2, temperature_exceeded, humidity_exceeded, co2_exceeded) SELECT\n" +
//                "id,room_id,measurement.date,temperature,humidity, co2,temperature_exceeded, humidity_exceeded,co2exceeded from measurement\n" +
//                "except select measurement_id, room_id, date, temperature, humidity, co2, temperature_exceeded, humidity_exceeded, co2_exceeded from stage_air4you.dim_measurement");
////        jdbcManager.execute("update stage_air4you.dim_measurement\n" +
////                "set r_id = dim_room.r_id\n" +
////                "from stage_air4you.dim_room\n" +
////                "where\n" +
////                " stage_air4you.dim_measurement.room_id = stage_air4you.dim_room.room_id");
//    }
//
//    public void extractRoomToStage(){
//        jdbcManager.execute("truncate table stage_air4you.dim_room  cascade");
//        jdbcManager.execute("Insert into stage_air4you.Dim_Room(\n" +
//                "room_id, name, registration_date, user_id\n" +
//                ")SELECT room.room_id, room.name, room.registration_date, room.user_id from room\n" +
//                "except select room_id, name, registration_date, user_id from stage_air4you.Dim_Room"
//
//        );
//    }
//    public void extractTokenToStage(){
//        jdbcManager.execute("insert into stage_air4you.dim_token " +
//                "select id, uid, token from tokens " +
//                "except select id, uid, token from stage_air4you.dim_token "
//        );
//    }
//
//
//
//
//    //Need to take data from Dim_Room and Dim_Token from stage.
//    public void extractDimUserToStage(){
//        jdbcManager.execute("insert into stage_air4you.Dim_User\n" +
//                "(user_id, token )" +
//                "                select stage_air4you.Dim_Room.user_id, stage_air4you.dim_token.token\n" +
//                "                from stage_air4you.Dim_Room\n" +
//                "                inner join stage_air4you.Dim_Token\n" +
//                "            on stage_air4you.Dim_Room.user_id = stage_air4you.Dim_Token.uid " +
//                "except select user_id, token from stage_air4you.Dim_User");
//    }
//
//    public void extractDimDateFromRoomToStage(){
//        jdbcManager.execute("\n" +
//                "insert into stage_air4you.dim_date(\n" +
//                " year, month, week, day, hour, minute, fact_type\n" +
//                ") select\n" +
//                "date_part('year', registration_date),\n" +
//                "date_part('month', registration_date),\n" +
//                "date_part('week', registration_date),\n" +
//                "date_part('day', registration_date),\n" +
//                "date_part('hour', registration_date),\n" +
//                "date_part('minute', registration_date),\n" +
//                "         'room_registartion'\n" +
//                "from stage_air4you.dim_room\n" +
//                "except select cast(year as double precision), cast(month as double precision),\n" +
//                "              cast(week as double precision),\n" +
//                "              cast(day as double precision),\n" +
//                "              cast(hour as double precision),\n" +
//                "              cast(minute as double precision),\n" +
//                "              fact_type\n" +
//                "from stage_air4you.dim_date");
//    }
//
//    public void extractDimDateFromMeasurementToStage(){
//        jdbcManager.execute("insert into stage_air4you.dim_date(\n" +
//                " year, month, week, day, hour, minute, fact_type\n" +
//                ") select\n" +
//                "--     extract(registration_date.date) as year\n" +
//                "date_part('year', date),\n" +
//                "date_part('month', date),\n" +
//                "date_part('week', date),\n" +
//                "date_part('day', date),\n" +
//                "date_part('hour', date),\n" +
//                "date_part('minute', date),\n" +
//                "'measurement'\n" +
//                "from stage_air4you.dim_measurement\n" +
//                "except select cast(year as double precision), cast(month as double precision),\n" +
//                "              cast(week as double precision),\n" +
//                "              cast(day as double precision),\n" +
//                "              cast(hour as double precision),\n" +
//                "              cast(minute as double precision),\n" +
//                "              fact_type\n" +
//                "              from stage_air4you.dim_date");
//    }
//
//    private void insertValidToAndValidFromToDimensionsStage() {
//        jdbcManager.update("update  stage_air4you.dim_room\n" +
//                "                set ValidFrom = to_char(CURRENT_DATE, 'YYYYMMDD')::integer,\n" +
//                "                ValidTo = 99990101;\n" +
//                "                update stage_air4you.dim_date\n" +
//                "                set ValidFrom = to_char(CURRENT_DATE, 'YYYYMMDD')::integer,\n" +
//                "                ValidTo = 99990101;\n" +
//                "                update stage_air4you.dim_token\n" +
//                "                set ValidFrom = to_char(CURRENT_DATE, 'YYYYMMDD')::integer,\n" +
//                "                validto = 99990101;\n" +
//                "                update stage_air4you.dim_measurement\n" +
//                "                set ValidFrom = to_char(CURRENT_DATE, 'YYYYMMDD')::integer,\n" +
//                "                validto = 99990101;\n" +
//                "                update stage_air4you.dim_user\n" +
//                "                set ValidFrom = to_char(CURRENT_DATE, 'YYYYMMDD')::integer,\n" +
//                "                validto = 99990101;");
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
