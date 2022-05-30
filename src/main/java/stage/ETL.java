package stage;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ETL {
    private JDBCManager jdbcManager = null;

    public ETL(){
        jdbcManager = JDBCManager.getInstance();

        createAndInsertLogTable();

        updateEdwDimensionWithValidToAndValidFrom();

        logUpdate();
    }

    private void updateEdwDimensionWithValidToAndValidFrom() {
    jdbcManager.execute("alter table edw_air4you.dim_measurement\n" +
            "add if not exists ValidFrom int, add if not exists ValidTo int;\n" +
            "alter table edw_air4you.dim_room\n" +
            "add if not exists ValidFrom int,  add if not exists ValidTo int;\n" +
            "alter table edw_air4you.dim_date\n" +
            "add  if not exists ValidFrom int,  add if not exists ValidTo int;\n" +
            "alter table edw_air4you.dim_token\n" +
            "add if not exists ValidFrom int,  add if not exists ValidTo int;\n" +
            "alter table edw_air4you.dim_user\n" +
            "add if not exists ValidFrom int, add if not exists ValidTo int\n" +
            ";\n" +
            "\n" +
            "update edw_air4you.dim_room\n" +
            "set ValidFrom = 20220101,\n" +
            "ValidTo = 99990101;\n" +
            "update edw_air4you.dim_date\n" +
            "set ValidFrom = 20220101,\n" +
            "validto = 99990101;\n" +
            "update edw_air4you.dim_token\n" +
            "set ValidFrom = 20220101,\n" +
            "validto = 99990101;\n" +
            "update edw_air4you.dim_measurement\n" +
            "set ValidFrom = 20220101,\n" +
            "validto = 99990101;\n" +
            "update edw_air4you.dim_user\n" +
            "set ValidFrom = 20220101,\n" +
            "validto = 99990101;");

    }

    private void createAndInsertLogTable() {
        jdbcManager.execute("create table if not exists etl_air4you.LogUpdate(\n" +
                "    table_name varchar(50) null,\n" +
                "    last_load_date int null\n" +
                ");\n" +
                "insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_measurement', 20220101);\n" +
                "insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_room', 20220101);\n" +
                "insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_date', 20220101);\n" +
                "insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_token', 20220101);\n" +
                "insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_user', 20220101); \n" +
                "insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('fact_measurement', 20220101);\n" +
                "insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('fact_registration', 20220101);\n"
        );
    }




    public void logUpdate(){


        Date date = new Date();
        SimpleDateFormat formator = new SimpleDateFormat("yyyyddMM");

        String query = "select max(last_load_date) from etl_air4you.LogUpdate where table_name='dim_measurement'";
        int LastLoad = jdbcManager.queryForObject(query, Integer.class);
        int NewDate = Integer.parseInt(formator.format(date));
        int FutureDate = 99990101;


        String countMeasurements = "select count(*) from edw_air4you.dim_measurement";
        int measurementsInEdw = jdbcManager.queryForObject(countMeasurements, Integer.class);


            jdbcManager.execute( "insert into edw_air4you.dim_measurement\n" +
                    "( room_id, date, temperature, humidity, co2, temperature_exceeded, humidity_exceeded, co2_exceeded, validfrom, validto)\n" +
                    "SELECT\n" +
                    "room_id,date,temperature,humidity, co2,temperature_exceeded, humidity_exceeded, co2_exceeded, "+ NewDate + ", " + FutureDate + "  from stage_air4you.dim_measurement where validto = 99990101\n" +
                    "except select  room_id, date, temperature, humidity, co2, temperature_exceeded, humidity_exceeded, co2_exceeded, validfrom, validto from edw_air4you.dim_measurement \n");
        jdbcManager.execute("insert into etl_air4you.LogUpdate(table_name, last_load_date) values ('dim_measurement', "+ NewDate + ");\n");
    }
}
