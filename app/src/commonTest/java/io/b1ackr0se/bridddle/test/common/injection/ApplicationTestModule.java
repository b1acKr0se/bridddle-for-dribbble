package io.b1ackr0se.bridddle.test.common.injection;


import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationTestModule {
    protected final Application application;

    public ApplicationTestModule(Application application) {
        this.application = application;
    }

    @Provides
    Application providesApplication() {
        return application;
    }

    @Provides
    Context provideContext() {
        return application;
    }
}
