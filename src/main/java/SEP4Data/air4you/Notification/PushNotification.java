package SEP4Data.air4you.Notification;

public class PushNotification {


    private Data data;
    private String to;

    public PushNotification( String to, Data data){
        this.to = to;
        this.data = data;
    }

    public PushNotification(Data data){
        this.data = data;
    }

    public void setNotification(Data data){
        this.data = data;
    }

    public Data getNotification(){
        return data;
    }

    public String getTo(){
        return to;
    }

    public void setTo(String recipient){
        this.to = recipient;
    }

}
