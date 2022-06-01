package stage;

import org.springframework.dao.DuplicateKeyException;

import java.sql.Date;

public class Load {

    private JDBCManager jdbcManager;
    private ETL etl;

    public Load(){
        jdbcManager = JDBCManager.getInstance();
        createEdwSchema();
        createEdwDimRoom();
        createEdwDimUser();
        createEdwDimToken();
        createEdwDimDate();
        createEdwDimMeasurement();
        addValidToandValidFromToEDWDimensions();


        insertIntoEdwDimRoom();
        insertIntoEdwDimDate();
        insertIntoEdwDimUser();
        insertIntoEdwDimToken();
        insertIntoEdwDimMeasurement();

        createEdwFactRoomRegistration();
        createEdwFactMeasurement();

        initialLoad();

        etl= new ETL();
    }

    /*Creating edw schema tables for all dimension and fact tables*/
    private void createEdwSchema(){
        jdbcManager.execute("CREATE SCHEMA IF NOT EXISTS edw_air4you");
    }

    private void createEdwFactMeasurement() {
        jdbcManager.execute("create table if not exists edw_air4you.fact_measurement( " +
                "m_id int not null, " +
                "u_id int not null, " +
                "r_id int not null, " +
                "d_id  int not null, " +
                "foreign key (u_id) references edw_air4you.dim_user (u_id) on delete cascade, " +
                "foreign key (r_id) references edw_air4you.dim_room (r_id) on delete cascade, " +
                "foreign key (d_id) references edw_air4you.dim_date (d_id) on delete cascade, " +
                "foreign key (m_id) references edw_air4you.dim_measurement (m_id) on delete cascade, " +
                "constraint fact_measurement_PK PRIMARY KEY(u_id, r_id, d_id, m_id));");
    }

    private void createEdwFactRoomRegistration() {
        jdbcManager.execute("create table if not exists edw_air4you.fact_registration(\n" +
                "                    u_id int not null,\n" +
                "                    r_id int not null,\n" +
                "                    d_id  int not null,\n" +
                "                    foreign key (u_id) references edw_air4you.dim_user (u_id) on delete cascade ,\n" +
                "                    foreign key (r_id) references edw_air4you.dim_room (r_id) on delete cascade,\n" +
                "                    foreign key (d_id) references edw_air4you.dim_date (d_id)  on delete cascade,\n" +
                "                    constraint Registration_PK PRIMARY KEY(u_id, r_id, d_id)\n" +
                "                    );");
    }

    private void createEdwDimUser() {
        jdbcManager.execute("create table if not exists edw_air4you.dim_user (\n" +
            "    u_id  serial  primary key ,\n" +
            "    user_id varchar(255),\n" +
            "    token varchar(255),\n" +
            "    email varchar(255),\n" +
            "    name varchar(255)\n" +
            ");");
    }

    private void createEdwDimToken() {
        jdbcManager.execute("create table if not exists edw_air4you.dim_token (\n" +
                "    t_id  serial  primary key ,\n" +
                "    token_id int,\n" +
                "    u_id varchar(255),\n" +
                "    token varchar(255)\n" +
                "\n" +
                ");");
    }

    private void createEdwDimMeasurement() {
        jdbcManager.execute("create table if not exists edw_air4you.dim_measurement (\n" +
                "    m_id  serial  primary key ,\n" +
                "    measurement_id int,\n" +
                "    room_id varchar(255),\n" +
                "    date timestamp,\n" +
                "    temperature numeric(6,2),\n" +
                "    humidity numeric(6,2),\n" +
                "    co2 numeric(6,2),\n" +
                "    temperature_exceeded boolean,\n" +
                "    humidity_exceeded boolean,\n" +
                "    co2_exceeded boolean\n" +
                ");");
    }

    private void createEdwDimDate() {
        jdbcManager.execute("create table if not exists edw_air4you.dim_date (\n" +
            "    d_id  serial  primary key ,\n" +
            "    date_id int,\n" +
            "    year int,\n" +
            "    month int,\n" +
            "    week int,\n" +
            "    day int,\n" +
            "    hour int,\n" +
            "    minute int,\n" +
            "    fact_type varchar(40)\n" +
            ");");
    }
    private void createEdwDimRoom() {
        jdbcManager.execute("create table if not exists edw_air4you.dim_room (\n" +
            "    r_id  serial  primary key ,\n" +
            "    room_id varchar(255) not null,\n" +
            "    name varchar(255) not null,\n" +
            "    registration_date timestamp,\n" +
            "    user_id varchar(255)\n" +
            ");");
    }


    /*LOADING - Populating the edw schema dimension tables with transformed data */
    private void insertIntoEdwDimDate() {
        jdbcManager.execute("insert into edw_air4you.dim_date (date_id, year, month, week, day, hour, minute, fact_type)\n" +
            "select date_id, year, month, week, day, hour, minute, fact_type from stage_air4you.dim_date\n" +
            "except select date_id, year, month, week, day, hour, minute, fact_type from edw_air4you.dim_date;");
    }

    private void insertIntoEdwDimUser() {
        jdbcManager.execute("\n" +
            "insert into edw_air4you.Dim_User\n" +
            "                (user_id, token, email, name  )\n" +
            "                                select user_id, token, email, name\n" +
            "                                from stage_air4you.dim_user\n" +
            "                except select user_id, token, email, name from edw_air4you.Dim_User;");
    }

    private void insertIntoEdwDimToken() {
        jdbcManager.execute("insert into edw_air4you.dim_token(u_id, token)\n" +
            "                select  uid, token from stage_air4you.dim_token\n" +
            "                except select u_id, token from edw_air4you.dim_token;");
    }

    private void insertIntoEdwDimMeasurement() {
        jdbcManager.execute("insert into edw_air4you.dim_measurement\n" +
            "                ( measurement_id, room_id, date, temperature, humidity, co2, temperature_exceeded, humidity_exceeded, co2_exceeded) SELECT\n" +
            "                measurement_id, room_id,date,temperature,humidity, co2,temperature_exceeded, humidity_exceeded, co2_exceeded from stage_air4you.dim_measurement\n" +
            "                except select measurement_id, room_id, date, temperature, humidity, co2, temperature_exceeded, humidity_exceeded, co2_exceeded from edw_air4you.dim_measurement;\n");
    }

    private void insertIntoEdwDimRoom() {
        jdbcManager.execute("Insert into edw_air4you.dim_room(room_id, name, registration_date, user_id, validfrom, validto)\n" +
                "            SELECT room_id, name, registration_date, user_id, to_char(CURRENT_DATE, 'YYYYMMDD')::integer, 99990101 from stage_air4you.dim_room\n" +
                "            where room_id in (select room_id from stage_air4you.dim_room except select room_id from edw_air4you.dim_room where edw_air4you.dim_room.validto = 99990101)\n");

    }


    /*Populating the edw fact tables with data*/
    public void extractFactMeasurementToEdw(){
    jdbcManager.execute("insert into  edw_air4you.fact_measurement(\n" +
            "                    m_id,\n" +
            "                    u_id,\n" +
            "                    r_id,\n" +
            "                    d_id\n" +
            "                )select\n" +
            "                    dim_measurement.m_id\n" +
            "                    ,dim_user.u_id,\n" +
            "                    dim_room.r_id,\n" +
            "                    dim_date.d_id\n" +
            "\n" +
            "                from\n" +
            "                     edw_air4you.dim_date,\n" +
            "                    edw_air4you.dim_room\n" +
            "                inner join edw_air4you.dim_user on dim_room.user_id = dim_user.user_id\n" +
            "                inner join edw_air4you.dim_measurement on dim_measurement.room_id = dim_room.room_id\n" +
            "                where  fact_type = 'measurement' and (EXTRACT(YEAR FROM dim_measurement.date) = dim_date.year ) and (EXTRACT(MONTH FROM dim_measurement.date) = dim_date.month )\n" +
            "                and (EXTRACT(DAY FROM dim_measurement.date) = dim_date.day ) and (EXTRACT(HOUR FROM dim_measurement.date) = dim_date.hour)\n" +
            "                  and (EXTRACT(MINUTE FROM dim_measurement.date) = dim_date.minute )\n" +
            "                -- and (dim_measurement.humidity_exceeded = true or dim_measurement.temperature_exceeded = true or dim_measurement.co2_exceeded = true)\n" +
            "                on conflict do nothing\n");
    }

    public void extractFactRegistrationToEdw(){
        try{
           jdbcManager.execute("insert into edw_air4you.fact_registration(\n" +
                   "                        u_id, r_id, d_id\n" +
                   "                    ) SELECT dim_user.u_id, dim_room.r_id, dim_date.d_id\n" +
                   "                             from edw_air4you.dim_date, edw_air4you.dim_user inner join edw_air4you.dim_room\n" +
                   "                    on dim_room.user_id = dim_user.user_id\n" +
                   "                    where fact_type = 'room_registartion' and (EXTRACT(YEAR FROM dim_room.registration_date) = dim_date.year ) and (EXTRACT(MONTH FROM dim_room.registration_date) = dim_date.month\n" +
                   "                    and (EXTRACT(DAY FROM dim_room.registration_date) = dim_date.day ) and (EXTRACT(HOUR FROM dim_room.registration_date) = dim_date.hour)\n" +
                   "                      and (EXTRACT(MINUTE FROM dim_room.registration_date) = dim_date.minute ))\n" +
                   "                    on conflict do nothing;");
        }catch(DuplicateKeyException e){
            throw e;
        }
    }

    /*Initial load of data*/
    public void initialLoad(){
        java.util.Date date = new java.util.Date();
        long timeInMilliSeconds = date.getTime();
        jdbcManager.execute(" create table if not exists edw_air4you.initial_load_Date(\n" +
            "        id serial primary key ,\n" +
            "        date timestamp,\n" +
            "        load_type varchar(255)\n" +
            "    );");
        String sqlMeasurement = "select count (*) from edw_air4you.fact_measurement";
        String sqlRegistartion = "    select count (*) from edw_air4you.fact_registration";
        int fact_measurement_rows = jdbcManager.queryForObject(sqlMeasurement, Integer.class);
        int fact_registartion_rows = jdbcManager.queryForObject(sqlRegistartion, Integer.class);
        if(fact_registartion_rows == 0){
            extractFactRegistrationToEdw();
            jdbcManager.update("insert into edw_air4you.initial_load_date(\n" +
                "    date, load_type\n" +
                ") values (?, ?)", new Date(timeInMilliSeconds), "FactRegistartion");
        }
        else if(fact_registartion_rows > 0){
            extractFactRegistrationToEdw();
        }
        if(fact_measurement_rows == 0){
            extractFactMeasurementToEdw();
            jdbcManager.update("insert into edw_air4you.initial_load_date(\n" +
                "    date, load_type\n" +
                ") values (?, ?)", new Date(timeInMilliSeconds), "FactMeasurement");
        }
        else if(fact_measurement_rows > 0){
            extractFactMeasurementToEdw();
        }
    }

    private void addValidToandValidFromToEDWDimensions() {
        jdbcManager.execute("alter table edw_air4you.dim_measurement\n" +
                "add if not exists ValidFrom int, add if not exists ValidTo int;\n" +
                "alter table edw_air4you.dim_room\n" +
                "add if not exists ValidFrom int, add if not exists ValidTo int;\n" +
                "alter table edw_air4you.dim_date\n" +
                "add  if not exists ValidFrom int,  add if not exists ValidTo int;\n" +
                "alter table edw_air4you.dim_token\n" +
                "add if not exists ValidFrom int,  add if not exists ValidTo int;\n" +
                "alter table edw_air4you.dim_user\n" +
                "add if not exists ValidFrom int, add if not exists ValidTo int\n" +
                ";\n");

    }



}
