package SEP4Data.air4you.Notification;


import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Service
public class MainActivity {

    public void sendNotification(String to,Data data){


        PushNotification pushNotification = new PushNotification(to, data);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NotificationAPI apiService = retrofit.create(NotificationAPI.class);
        Call<String> call = apiService.postNotification(pushNotification);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    return;
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

}
