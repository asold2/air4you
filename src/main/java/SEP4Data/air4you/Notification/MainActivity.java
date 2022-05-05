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

    public void sendNotification(String title, String content){

        Notification message = new Notification(title, content);
        PushNotification data = new PushNotification(message,"eFv5k7HsQWucVQ0cs7E7Qh:APA91bFIcI0YG6qIcIesNdzbZPA_jnmu_pctycm_hG-QkgegVm3CeQr0KNSc1gYD3oEcqXmv3r5EZcA4z_QTbgnhnkal2b-eN5z9PdI88K6OPg21D0Hw9y6aXA_aqgHsfMum76isg09D");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NotificationAPI apiService = retrofit.create(NotificationAPI.class);
        Call<String> call = apiService.postNotification(data);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    System.out.println(String.valueOf(response.code()));
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
