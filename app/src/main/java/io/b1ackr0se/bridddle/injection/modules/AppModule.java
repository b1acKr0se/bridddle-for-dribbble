package io.b1ackr0se.bridddle.injection.modules;


import android.app.Application;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.b1ackr0se.bridddle.BuildConfig;
import io.b1ackr0se.bridddle.data.remote.dribbble.AuthInterceptor;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    DribbbleApi providesClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new AuthInterceptor(BuildConfig.DRIBBBLE_ACCESS_TOKEN))
                .build();
        return new Retrofit.Builder()
                .baseUrl(DribbbleApi.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create((DribbbleApi.class));
    }

}
