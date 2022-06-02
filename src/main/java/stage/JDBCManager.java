//package stage;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//
///*This class implements a Singleton, to make sure it is instantiated only once*/
//public class JDBCManager extends JdbcTemplate {
//    private static JDBCManager instance;
//    private StageJDBC stageJDBC;
//
//    private JDBCManager(){
//        stageJDBC = new StageJDBC();
//        this.setDataSource(stageJDBC.stageDataSource());
//
//    }
//
//    public static JDBCManager getInstance(){
//        if(instance==null){
//            instance = new JDBCManager();
//        }
//    return instance;
//    }
//}
