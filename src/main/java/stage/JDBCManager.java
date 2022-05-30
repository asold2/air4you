package stage;

import SEP4Data.air4you.StageJDBC;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
/*This class implements a Singleton, to make sure it is instantiated only once*/
public class JDBCManager extends JdbcTemplate {
    private static JDBCManager instance;
    private StageJDBC stageJDBC = null;

    private JDBCManager(){
        stageJDBC = new StageJDBC();
        this.setDataSource(stageJDBC.stageDataSource());

    }

    public static JDBCManager getInstance(){
        if(instance==null){
            instance = new JDBCManager();
        }
    return instance;
    }
}
