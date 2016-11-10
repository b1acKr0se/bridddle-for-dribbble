package io.b1ackr0se.bridddle.injection.components;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import io.b1ackr0se.bridddle.App;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import io.b1ackr0se.bridddle.injection.modules.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(App application);

    Application application();

    DribbbleApi client();
}