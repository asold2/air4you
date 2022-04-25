package SEP4Data.air4you.user;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "registered_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @NotNull
    private String username;
    @NotNull
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(){}

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
