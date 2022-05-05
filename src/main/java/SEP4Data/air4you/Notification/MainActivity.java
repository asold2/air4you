package SEP4Data.air4you.Notification;


import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class MainActivity {

    public void sendNotification(String title, String content){

        Notification message = new Notification(title, content);
        PushNotification data = new PushNotification(message,"dlfvYOSQQeChIa1JJQ93hR:APA91bEMPjqM4a6ZavXhHsLVO1EG31QZbAl5QVB4BvAqgzZE0jCx68DgojyKgYhPJl5yDxpYzd7cp0PMrZRqMLG0_8hMpSKQ3iYqmEXBO8Pp6wZwU_tgqvTVcKPoc6qhQhyRBot67mMe");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NotificationAPI apiService = retrofit.create(NotificationAPI.class);
        Call<Response> call = apiService.postNotification(data);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {
                if(!response.isSuccessful()){
                    System.out.println(String.valueOf(response.code()));
                    return;
                }
            }
            @Override
            public void onFailure(Call<Response> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

}
