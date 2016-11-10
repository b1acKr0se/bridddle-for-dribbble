package io.b1ackr0se.bridddle.base;

import android.support.v7.app.AppCompatActivity;

import io.b1ackr0se.bridddle.App;
import io.b1ackr0se.bridddle.injection.components.ActivityComponent;
import io.b1ackr0se.bridddle.injection.components.DaggerActivityComponent;
import io.b1ackr0se.bridddle.injection.modules.ActivityModule;


public abstract class BaseActivity extends AppCompatActivity {
    private ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .appComponent(App.getApplication(this).getAppComponent())
                    .build();
        }
        return activityComponent;
    }
}
