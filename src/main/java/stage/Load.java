package stage;

import org.springframework.dao.DuplicateKeyException;

import java.sql.Date;

public class Load {

    private JDBCManager jdbcManager = null;
    private ETL etl = null;

    public Load(){
        jdbcManager = JDBCManager.getInstance();
        creatEdwDimRoom();
        creatEdwDimUser();
        creatEdwDimToken();
        creatEdwDimDate();
        creatEdwDimMeasurement();

        insertIntoEdwDimRoom();
        insertIntoEdwDimDate();
        insertIntoEdwDimUser();
        insertIntoEdwDimToken();
        insertIntoEdwDimMeasurement();


        createEdwFactRoomRegistartion();
        createEdwFactMeasurement();


        initialLoad();

        etl= new ETL();
    }

    private void createEdwFactMeasurement() {
        jdbcManager.execute("create table if not exists edw_air4you.fact_measurement(\n" +
                "                                                                   m_id int not null,\n" +
                "                                                                    u_id int not null,\n" +
                "                                                                    r_id int not null,\n" +
                "                                                                    d_id  int not null,\n" +
                "                                                                    foreign key (u_id) references edw_air4you.dim_user (u_id) on delete cascade,\n" +
                "                                                                    foreign key (r_id) references edw_air4you.dim_room (r_id) on delete cascade,\n" +
                "                                                                    foreign key (d_id) references edw_air4you.dim_date (d_id) on delete cascade,\n" +
                "                                                                    foreign key (m_id) references edw_air4you.dim_measurement (m_id) on delete cascade,\n" +
                "                                                                    constraint fact_measurement_PK PRIMARY KEY(u_id, r_id, d_id, m_id)\n" +
                "                                                                    );");
    }

    private void createEdwFactRoomRegistartion() {
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
        jdbcManager.execute("insert into edw_air4you.dim_token(token_id, u_id, token)\n" +
                "                select  id, uid, token from stage_air4you.dim_token\n" +
                "                except select token_id, u_id, token from edw_air4you.dim_token;");
    }

    private void insertIntoEdwDimMeasurement() {
        jdbcManager.execute("insert into edw_air4you.dim_measurement\n" +
                "                ( measurement_id, room_id, date, temperature, humidity, co2, temperature_exceeded, humidity_exceeded, co2_exceeded) SELECT\n" +
                "                measurement_id, room_id,date,temperature,humidity, co2,temperature_exceeded, humidity_exceeded, co2_exceeded from stage_air4you.dim_measurement\n" +
                "                except select measurement_id, room_id, date, temperature, humidity, co2, temperature_exceeded, humidity_exceeded, co2_exceeded from edw_air4you.dim_measurement;\n");
    }

    private void insertIntoEdwDimRoom() {
        jdbcManager.execute("Insert into edw_air4you.dim_room(room_id, name, registration_date, user_id)\n" +
                "                SELECT room_id, name, registration_date, user_id from stage_air4you.dim_room\n" +
                "                               except select room_id, name, registration_date, user_id from edw_air4you.Dim_Room;\n");
    }

    private void creatEdwDimUser() {
    jdbcManager.execute("create table if not exists edw_air4you.dim_user (\n" +
            "    u_id  serial  primary key ,\n" +
            "    user_id varchar(255),\n" +
            "    token varchar(255),\n" +
            "    email varchar(255),\n" +
            "    name varchar(255)\n" +
            ");");
    }

    private void creatEdwDimToken() {
        jdbcManager.execute("create table if not exists edw_air4you.dim_token (\n" +
                "    t_id  serial  primary key ,\n" +
                "    token_id int,\n" +
                "    u_id varchar(255),\n" +
                "    token varchar(255)\n" +
                "\n" +
                ");");
    }

    private void creatEdwDimMeasurement() {
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

    private void creatEdwDimDate() {
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

    private void creatEdwDimRoom() {
        jdbcManager.execute("create table if not exists edw_air4you.dim_room (\n" +
                "    r_id  serial  primary key ,\n" +
                "    room_id varchar(255) not null,\n" +
                "    name varchar(255) not null,\n" +
                "    registration_date timestamp,\n" +
                "    user_id varchar(255)\n" +
                ");");

    }


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

        System.out.println(fact_registartion_rows);
        System.out.println(fact_measurement_rows);


        if(fact_registartion_rows == 0){
            extractFactRegistrationToEdw();
            jdbcManager.update("insert into edw_air4you.initial_load_date(\n" +
                    "    date, load_type\n" +
                    ") values (?, ?)", new Date(timeInMilliSeconds), "FactRegistartion");

        }
        if(fact_measurement_rows == 0){
            extractFactMeasurementToEdw();
            jdbcManager.update("insert into edw_air4you.initial_load_date(\n" +
                    "    date, load_type\n" +
                    ") values (?, ?)", new Date(timeInMilliSeconds), "FactMeasurement");
        }


    }

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

}
