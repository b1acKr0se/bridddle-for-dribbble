package io.b1ackr0se.bridddle.ui.login;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.BuildConfig;
import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.model.AccessToken;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleAuthenticator;
import io.b1ackr0se.bridddle.util.AuthenticationManager;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginPresenter extends BasePresenter<LoginView> {
    AuthenticationManager authenticationManager;
    DribbbleAuthenticator dribbbleApi;

    @Inject
    public LoginPresenter(DribbbleAuthenticator authenticator, AuthenticationManager pref) {
        dribbbleApi = authenticator;
        authenticationManager = pref;
    }

    public void getAccessToken(String code) {
        dribbbleApi.getAccessToken(BuildConfig.DRIBBBLE_CLIENT_ID, BuildConfig.DRIBBBLE_CLIENT_SECRET, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AccessToken>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onError();
                    }

                    @Override
                    public void onNext(AccessToken accessToken) {
                        authenticationManager.saveAccessToken(accessToken.accessToken);
                        getView().onSuccess();
                    }
                });
    }

}
