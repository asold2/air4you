//package stage;
//
//public class Transform {
//
//    private JDBCManager jdbcManager;
//    private Load load;
//
//    public Transform() {
//        jdbcManager = JDBCManager.getInstance();
//
//        transformUserEmailandName();
//        transformDimMeasurementWrongDate();
//        transformDimDateWrongDate();
//
//
//        load = new Load();
//    }
//
//
//    /*Updating the values for email and name where they are null*/
//    public void transformUserEmailandName(){
//        jdbcManager.execute("update stage_air4you.dim_user\n" +
//                "set email = 'unknown'\n" +
//                "where email is null;\n" +
//                "update stage_air4you.dim_user\n" +
//                "set name = 'not specified'\n" +
//                "where name is null;");
//    }
//
//    /*Updating the Measurement stage table, so it can contain the right dates*/
//    public void transformDimMeasurementWrongDate(){
//        jdbcManager.execute("delete  from stage_air4you.dim_measurement\n" +
//                "    where extract(year from stage_air4you.dim_measurement.date) < 2022;");
//    }
//    public void transformDimDateWrongDate(){
//        jdbcManager.execute("delete  from stage_air4you.dim_date\n" +
//                "    where year < 2022;");
//    }
//}
