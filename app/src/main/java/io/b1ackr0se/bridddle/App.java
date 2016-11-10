package io.b1ackr0se.bridddle;


import android.app.Application;
import android.content.Context;

import io.b1ackr0se.bridddle.injection.components.AppComponent;
import io.b1ackr0se.bridddle.injection.components.DaggerAppComponent;
import io.b1ackr0se.bridddle.injection.modules.AppModule;

public class App extends Application {
    AppComponent appComponent;

    @Override public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }

    public static App getApplication(Context context) {
        return (App) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
