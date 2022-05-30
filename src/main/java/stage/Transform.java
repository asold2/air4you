package stage;

public class Transform {

    private JDBCManager jdbcManager = null;
    private Load load = null;

    public Transform() {
        jdbcManager = JDBCManager.getInstance();

        transformUserEmailandName();
        transformDimMeasurementWrongDate();
        transformDimDateWrongDate();

        addValidToandValidFromToStageDimensions();

        load = new Load();
    }

    /*Adding extra attribute to staging tables to reflect type two changes*/
    private void addValidToandValidFromToStageDimensions() {
        jdbcManager.execute("alter table stage_air4you.dim_measurement\n" +
                "add if not exists ValidFrom int, add if not exists ValidTo int;\n" +
                "alter table stage_air4you.dim_room\n" +
                "add if not exists ValidFrom int,  add if not exists ValidTo int;\n" +
                "alter table stage_air4you.dim_date\n" +
                "add  if not exists ValidFrom int,  add if not exists ValidTo int;\n" +
                "alter table stage_air4you.dim_token\n" +
                "add if not exists ValidFrom int,  add if not exists ValidTo int;\n" +
                "alter table stage_air4you.dim_user\n" +
                "add if not exists ValidFrom int, add if not exists ValidTo int\n" +
                ";\n" +
                "\n" +
                "update stage_air4you.dim_room\n" +
                "set ValidFrom = 20220101,\n" +
                "ValidTo = 99990101;\n" +
                "update stage_air4you.dim_date\n" +
                "set ValidFrom = 20220101,\n" +
                "validto = 99990101;\n" +
                "update stage_air4you.dim_token\n" +
                "set ValidFrom = 20220101,\n" +
                "validto = 99990101;\n" +
                "update stage_air4you.dim_measurement\n" +
                "set ValidFrom = 20220101,\n" +
                "validto = 99990101;\n" +
                "update stage_air4you.dim_user\n" +
                "set ValidFrom = 20220101,\n" +
                "validto = 99990101;");
    }

    /*Updating the values for email and name where they are null*/
    public void transformUserEmailandName(){
        jdbcManager.execute("update stage_air4you.dim_user\n" +
                "set email = 'unknown'\n" +
                "where email is null;\n" +
                "update stage_air4you.dim_user\n" +
                "set name = 'not specified'\n" +
                "where name is null;");
    }

    /*Updating the Measurement stage table, so it can contain the right dates*/
    public void transformDimMeasurementWrongDate(){
        jdbcManager.execute("delete  from stage_air4you.dim_measurement\n" +
                "    where extract(year from stage_air4you.dim_measurement.date) < 2022;");
    }
    public void transformDimDateWrongDate(){
        jdbcManager.execute("delete  from stage_air4you.dim_date\n" +
                "    where year < 2022;");
    }
}
