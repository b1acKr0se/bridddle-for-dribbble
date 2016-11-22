package io.b1ackr0se.bridddle.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import io.b1ackr0se.bridddle.App;
import io.b1ackr0se.bridddle.MainActivity;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_LOGIN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                onLoggedIn();
            }
        }
    }

    protected abstract void onLoggedIn();
}
