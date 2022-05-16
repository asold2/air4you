package SEP4Data.air4you.Notification.Token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
@Table(name = "user_token")
public class UserToken {
    @SerializedName("uID")
    @Expose
    private String uId;

    @SerializedName("token")
    @Expose
    private String token;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    public UserToken(String uId, String token) {
        this.uId = uId;
        this.token = token;
    }

    public UserToken() {

    }

    public int getId() {
        return Id;
    }

    public String getuId() {
        return uId;
    }

    public void setId(String uId) {
        this.uId = uId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}