package io.b1ackr0se.bridddle;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import io.b1ackr0se.bridddle.data.model.AccessToken;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleAuthenticator;
import io.b1ackr0se.bridddle.test.common.MockModel;
import io.b1ackr0se.bridddle.test.common.RxSchedulersOverrideRule;
import io.b1ackr0se.bridddle.ui.login.LoginPresenter;
import io.b1ackr0se.bridddle.ui.login.LoginView;
import io.b1ackr0se.bridddle.util.AuthenticationManager;
import rx.Observable;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock LoginView loginView;
    @Mock AuthenticationManager authenticationManager;
    @Mock DribbbleAuthenticator authenticator;

    private LoginPresenter loginPresenter;

    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setup() {
        loginPresenter = new LoginPresenter(authenticator, authenticationManager);
        loginPresenter.attachView(loginView);
    }

    @After
    public void detachView() {
        loginPresenter.detachView();
    }

    @Test
    public void testLogInSuccessful() {
        String mockCode = UUID.randomUUID().toString();

        AccessToken mockAccessToken = MockModel.newAccessToken();

        doReturn(Observable.just(mockAccessToken))
                .when(authenticator)
                .getAccessToken(BuildConfig.DRIBBBLE_CLIENT_ID, BuildConfig.DRIBBBLE_CLIENT_SECRET, mockCode);

        loginPresenter.getAccessToken(mockCode);

        verify(authenticationManager).saveAccessToken(mockAccessToken.accessToken);
        verify(loginView).onSuccess();

    }

    @Test
    public void testLogInFailed() {
        String mockCode = UUID.randomUUID().toString();

        doReturn(Observable.error(new RuntimeException()))
                .when(authenticator)
                .getAccessToken(BuildConfig.DRIBBBLE_CLIENT_ID, BuildConfig.DRIBBBLE_CLIENT_SECRET, mockCode);

        loginPresenter.getAccessToken(mockCode);

        verify(authenticationManager, never()).saveAccessToken(anyString());
        verify(loginView).onError();

    }


}
