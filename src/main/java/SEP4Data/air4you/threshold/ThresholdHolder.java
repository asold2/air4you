package SEP4Data.air4you.threshold;

import stage.JDBCManager;

public class ThresholdHolder {
    private static  ThresholdHolder instance;
    private Threshold threshold;
     private  ThresholdHolder (){
     }
    public static ThresholdHolder getInstance(){
        if(instance==null){
            instance = new ThresholdHolder();
        }
        return instance;
    }

    public Threshold getThreshold() {
        return threshold;
    }

    public void setThreshold(Threshold threshold) {
        System.out.println("               SET THREHOLD");
        this.threshold = threshold;
    }
}
