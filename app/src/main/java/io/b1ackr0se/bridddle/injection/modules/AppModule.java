package io.b1ackr0se.bridddle.injection.modules;


import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleAuthenticator;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleSearch;
import io.b1ackr0se.bridddle.util.AuthenticationManager;
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
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    AuthenticationManager provideAuthenticationManager(Application application) {
        return new AuthenticationManager(application);
    }

    @Provides
    @Singleton
    DribbbleApi provideClient(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(DribbbleApi.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create((DribbbleApi.class));
    }

    @Provides
    @Singleton
    DribbbleAuthenticator provideAuthenticator(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(DribbbleAuthenticator.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create((DribbbleAuthenticator.class));
    }

    @Provides
    @Singleton
    DribbbleSearch provideSearch(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(DribbbleSearch.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create((DribbbleSearch.class));
    }
}
