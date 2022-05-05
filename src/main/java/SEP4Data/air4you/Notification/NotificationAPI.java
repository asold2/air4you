package SEP4Data.air4you.Notification;


import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@Service
public interface NotificationAPI {
    @Headers({"Authorization: key=" + Constants.SERVER_KEY, "Content-Type:" + Constants.CONTENT_TYPE})
    @POST("fcm/send")
    Call<Response> postNotification(@Body PushNotification data);
}
