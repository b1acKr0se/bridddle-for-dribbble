package io.b1ackr0se.bridddle.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.BuildConfig;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;


public class DribbbleLoginActivity extends BaseActivity implements View.OnClickListener, LoginView {
    public static final String LOGIN_CALLBACK = "auth-callback";
    public static final String LOGIN_URL = "https://dribbble.com/oauth/authorize?client_id="
            + BuildConfig.DRIBBBLE_CLIENT_ID
            + "&redirect_uri=plaid%3A%2F%2F" + LOGIN_CALLBACK
            + "&scope=public+write+comment+upload";

    @BindView(R.id.login) Button login;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.root) FrameLayout root;

    @Inject LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dribbble_login);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        presenter.attachView(this);

        login.setOnClickListener(this);

        if (getIntent().getBooleanExtra("command_login", false)) {
            doLogin();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null
                && intent.getData() != null
                && !TextUtils.isEmpty(intent.getData().getAuthority())
                && LOGIN_CALLBACK.equals(intent.getData().getAuthority())) {
            presenter.getAccessToken(intent.getData().getQueryParameter("code"));
        } else {
            dismiss();
        }
    }

    private void doLogin() {
        TransitionManager.beginDelayedTransition(root);
        login.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LOGIN_URL)));
    }

    private void dismiss() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                doLogin();
                break;
        }
    }

    @Override
    public void onSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onError() {
        dismiss();
    }

    @Override
    protected void onLoggedIn() {

    }
}
