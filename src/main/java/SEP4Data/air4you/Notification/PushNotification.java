package SEP4Data.air4you.Notification;

public class PushNotification {

    private Notification notification;
    private String to;

    public PushNotification(Notification notification, String to){
        this.notification = notification;
        this.to = to;
    }

    public PushNotification(Notification data){
        this.notification = data;
    }

    public void setNotification(Notification data){
        this.notification = data;
    }

    public Notification getNotification(){
        return notification;
    }

    public String getTo(){
        return to;
    }

    public void setTo(String recipient){
        this.to = recipient;
    }

}
