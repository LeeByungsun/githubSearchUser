package co.rgp.rgptest;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import co.rgp.rgptest.Const.constValue;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bslee on 2017-08-03.
 */

public class ApplicationClass extends Application {
    public static GithubApiInfo apiInfo_GithubServer = null;
    /**
     * Retrofit2 관련 변수 (자체 서버)
     */
    private static Retrofit retrofit_GithubServer = null;
    private Context context;

    public ApplicationClass(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);  // <-- this is the important line!

//        Retrofit retrofit_GithubServer = new Retrofit.Builder()
//                .baseUrl(constValue.GITHUB_URL + constValue.SEARCH)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(httpClient.build())
//                .build();
        retrofit_GithubServer = new Retrofit.Builder().baseUrl(constValue.GITHUB_URL + constValue.SEARCH)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build()).build();
        apiInfo_GithubServer = retrofit_GithubServer.create(GithubApiInfo.class);
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
