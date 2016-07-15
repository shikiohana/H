package changhonginterface;

import model.ChanghongUser;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Administrator on 2016/7/15.
 */
public interface ChanghongApiService {

   /* public interface GetToApi{
        @GET("service/getIpInfo.php")
        Call<ChanghongUser> getIpInfo(@Query("ip") String ip);
    }*/
   /* @GET("service/getIpInfo.php")
    Call<ChanghongUser> getIpInfo(@Query("ip") String ip);*/

       @POST("vip/reg")
       void creatUser(@Body ChanghongUser user, Callback<ChanghongUser> cb);


}
