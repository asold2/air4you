package SEP4Data.air4you.Notification.Token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.springframework.boot.context.properties.bind.Name;

import javax.persistence.*;

@Entity
@Table(name = "tokens")
public class UserToken {

    @SerializedName("uid")
    private String uid = "none";
    @SerializedName("token")
    private String token;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    public UserToken(String uid, String token) {
        this.uid = uid;
        this.token = token;
    }

    public UserToken() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getId() {
        return Id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        Id = id;
    }
}