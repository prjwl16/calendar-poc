package ttmm.network;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GoogleAPIs {

    @GET("oauth2/v3/userinfo")
    Call<JsonObject> getUserInfo(@Header("Authorization") String bearerToken);

}
