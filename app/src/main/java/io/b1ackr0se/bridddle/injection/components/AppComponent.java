package io.b1ackr0se.bridddle.injection.components;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import io.b1ackr0se.bridddle.App;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleAuthenticator;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleSearch;
import io.b1ackr0se.bridddle.injection.modules.AppModule;
import io.b1ackr0se.bridddle.util.AuthenticationManager;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(App application);

    Application application();

    DribbbleApi client();

    DribbbleAuthenticator authenticator();

    DribbbleSearch search();

    AuthenticationManager authenticationManager();
}