//package stage;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class ETL {
//    private JDBCManager jdbcManager;
//
//    public ETL(){
//        jdbcManager = JDBCManager.getInstance();
//        createETLSchema();
//        createAndInsertLogTable();
//        type2ChangesForRoom();
//        type2ChangesForToken();
//        type2ChangesForUser();
//
//
//    }
//
//    private void createETLSchema(){
//        jdbcManager.execute("CREATE SCHEMA IF NOT EXISTS etl_air4you");
//    }
//
//    private void type2ChangesForRoom(){
//
//        jdbcManager.execute("create table temp1 (\n" +
//                "                room_id VARCHAR(255) not null,\n" +
//                "                name VARCHAR(255),\n" +
//                "                registration_date timestamp,\n" +
//                "                user_id varchar(255)\n" +
//                "\n" +
//                ")");
//
//        jdbcManager.execute("insert into temp1(room_id, name, registration_date, user_id) SELECT room_id, name, registration_date, user_id\n" +
//                "from stage_air4you.dim_room except select room_id, name, registration_date, user_id from edw_air4you.dim_room\n" +
//                "where validto = 99990101\n" +
//                "except select room_id, name, registration_date, user_id from stage_air4you.dim_room\n" +
//                "where room_id in (select room_id from stage_air4you.dim_room except select room_id from edw_air4you.dim_room where edw_air4you.dim_room.validto = 99990101);\n" +
//                "insert into edw_air4you.dim_room (room_id, name, registration_date, user_id, validfrom, validto)\n" +
//                "select room_id, name, registration_date, user_id, to_char(CURRENT_DATE, 'YYYYMMDD')::integer, 99990101\n" +
//                "from temp1;");
//        jdbcManager.update("update edw_air4you.dim_room\n" +
//                "set validto = 12\n" +
//                "where room_id in (select room_id from temp1) and dim_room.validfrom < to_char(CURRENT_DATE, 'YYYYMMDD')::integer;\n" +
//                "drop table if exists temp1");
//
//    }
//
//
//    private void type2ChangesForToken(){
//        jdbcManager.execute("with u as (\n" +
//                "      update edw_air4you.dim_token t\n" +
//                "          set validto = st.validfrom\n" +
//                "          from stage_air4you.dim_token st\n" +
//                "          where st.id = t.token_id and\n" +
//                "            t.validto = 99990101\n" +
//                "             )\n" +
//                "insert into edw_air4you.dim_token (token_id, u_id, token, validfrom, validto)\n" +
//                "     select id, uid ,token, to_char(CURRENT_DATE, 'YYYYMMDD')::integer, 99990101\n" +
//                "     from stage_air4you.dim_token sc;");
//    }
//
//    private void type2ChangesForUser(){
//        jdbcManager.execute("with u as (\n" +
//                "      update edw_air4you.dim_user u\n" +
//                "          set validto = su.validfrom\n" +
//                "          from stage_air4you.dim_user su\n" +
//                "          where su.u_id = u.u_id and\n" +
//                "            u.validto = 99990101\n" +
//                "             )\n" +
//                "insert into edw_air4you.dim_user (user_id, token, email, name, validfrom, validto)\n" +
//                "     select  user_id, token, email, name, to_char(CURRENT_DATE, 'YYYYMMDD')::integer, 99990101\n" +
//                "     from stage_air4you.dim_user sc;");
//    }
//    // Altering all tables within the edw schema with attributes to handle type 2 changes
//
//
//    /*Creating the ETL log table*/
//    private void createAndInsertLogTable() {
//
//
//        jdbcManager.execute("create table if not exists etl_air4you.LogUpdate(\n" +
//                "                    table_name varchar(50) null,\n" +
//                "                    last_load_date int null\n" +
//                "                );\n" +
//                "\n" +
//                "                insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_measurement', (select max(validfrom) from stage_air4you.dim_measurement)) on conflict do nothing ;\n" +
//                "                insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_room', (select max(validfrom) from stage_air4you.dim_room)) on conflict do nothing;\n" +
//                "                insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_date', (select max(validfrom) from stage_air4you.dim_date)) on conflict do nothing;\n" +
//                "                insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_token', (select max(validfrom) from stage_air4you.dim_token)) on conflict do nothing;\n" +
//                "                insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_user', (select max(validfrom) from stage_air4you.dim_user)) on conflict do nothing;\n" +
//                "                insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('fact_measurement', (select max(validfrom) from stage_air4you.dim_measurement)) on conflict do nothing;\n" +
//                "                insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('fact_registration', (select max(validfrom) from stage_air4you.dim_room)) on conflict do nothing;\n"
//        );
//    }
//
//
//}
