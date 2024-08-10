package ttmm.network;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public enum Google {
    INSTANCE;

    private GoogleAPIs googleAPIs;

    public GoogleAPIs getBase() {

        if (googleAPIs != null) {
            return googleAPIs;
        }

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .build();

        googleAPIs = retrofit.create(GoogleAPIs.class);
        return googleAPIs;
    }
}
