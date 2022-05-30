package stage;

public class Transform {

    private JDBCManager jdbcManager = null;
    private Load load = null;

    public Transform() {
        jdbcManager = JDBCManager.getInstance();

        transformUserEmailandName();
        transformDim_measurementWrongDate();
        transformDim_DateWrongDate();

        addValidToandValidFromToStageDimensions();

        load = new Load();
    }

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

    public void transformUserEmailandName(){
        jdbcManager.execute("update stage_air4you.dim_user\n" +
                "set email = 'unknown'\n" +
                "where email is null;\n" +
                "update stage_air4you.dim_user\n" +
                "set name = 'not specified'\n" +
                "where name is null;");
    }
    public void transformDim_measurementWrongDate(){
        jdbcManager.execute("delete  from stage_air4you.dim_measurement\n" +
                "    where extract(year from stage_air4you.dim_measurement.date) < 2022;");
    }
    public void transformDim_DateWrongDate(){
        jdbcManager.execute("delete  from stage_air4you.dim_date\n" +
                "    where year < 2022;");
    }
}
