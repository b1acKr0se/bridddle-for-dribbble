package io.b1ackr0se.bridddle.injection.modules;


import android.app.Application;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.b1ackr0se.bridddle.data.remote.dribbble.AuthInterceptor;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleAuthenticator;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleSearch;
import io.b1ackr0se.bridddle.util.SharedPref;
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
    OkHttpClient providesHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new AuthInterceptor(providesSharedPreferences().getAccessToken()))
                .build();
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    SharedPref providesSharedPreferences() {
        return new SharedPref(application);
    }

    @Provides
    @Singleton
    DribbbleApi providesClient() {
        return new Retrofit.Builder()
                .baseUrl(DribbbleApi.ENDPOINT)
                .client(providesHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create((DribbbleApi.class));
    }

    @Provides
    @Singleton
    DribbbleAuthenticator providesAuthenticator() {
        return new Retrofit.Builder()
                .baseUrl(DribbbleAuthenticator.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create((DribbbleAuthenticator.class));
    }

    @Provides
    @Singleton
    DribbbleSearch providesSearch() {
        return new Retrofit.Builder()
                .baseUrl(DribbbleSearch.ENDPOINT)
                .client(providesHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create((DribbbleSearch.class));
    }
}
