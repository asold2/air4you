package SEP4Data.air4you.Notification;

public class Data {

    String body;
    String title;
    boolean exceeded;

    public Data(String body, String title, boolean exceeded){
        this.body = body;
        this.title = title;
        this.exceeded = exceeded;
    }

    public Data() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExceeded() {
        return exceeded;
    }

    public void setExceeded(boolean exceeded) {
        this.exceeded = exceeded;
    }
}
