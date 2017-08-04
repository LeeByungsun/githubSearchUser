package co.rgp.rgptest;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import co.rgp.rgptest.Const.constValue;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bslee on 2017-08-03.
 */

public class ApplicationClass extends Application {
    /**
     * Retrofit2 관련 변수
     */
    private final static Retrofit retrofit_GithubServer = new Retrofit.Builder().baseUrl(constValue.GITHUB_URL + constValue.SEARCH)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    public final static GithubApiInfo apiInfo_GithubServer = retrofit_GithubServer.create(GithubApiInfo.class);
    private Context context;

    public ApplicationClass(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

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
