package io.b1ackr0se.bridddle;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.model.User;
import io.b1ackr0se.bridddle.data.remote.dribbble.DataManager;
import io.b1ackr0se.bridddle.test.common.MockModel;
import io.b1ackr0se.bridddle.test.common.RxSchedulersOverrideRule;
import io.b1ackr0se.bridddle.ui.profile.ProfilePresenter;
import io.b1ackr0se.bridddle.ui.profile.ProfileView;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ProfilePresenterTest {
    @Mock DataManager dataManager;
    @Mock ProfileView view;

    private ProfilePresenter presenter;

    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new ProfilePresenter(dataManager);
        presenter.attachView(view);
    }

    @After
    public void detach() {
        presenter.detachView();
    }

    @Test
    public void getUserFailedTheFirstTime() {
        when(dataManager.getAuthenticatedUser())
                .thenReturn(Observable.error(new RuntimeException()));

        presenter.getAuthUser();

        verify(view).showProgress(false);
        verify(view, never()).showProfile(any(User.class));
    }


    @Test
    public void getUserSuccessfullyTheFirstTime() {
        User user = MockModel.newUser();

        when(dataManager.getAuthenticatedUser())
                .thenReturn(Observable.just(user));

        when(dataManager.getUserLikes())
                .thenReturn(Observable.just(MockModel.newLikedShotList(10)));

        presenter.getAuthUser();

        verify(view).showProfile(user);
    }

    @Test
    public void getCachedUserSuccessfully() {
        User user = MockModel.newUser();

        when(dataManager.getAuthenticatedUser())
                .thenReturn(Observable.just(user));

        when(dataManager.getUserLikes())
                .thenReturn(Observable.just(MockModel.newLikedShotList(10)));

        presenter.getAuthUser();

        presenter.getAuthUser(false);

        verify(view, atLeast(2)).showProfile(user);
        verify(view, atMost(1)).showLikedShots(anyListOf(Shot.class));
    }

    @Test
    public void doNotGetCachedUser() {
        User user = MockModel.newUser();

        when(dataManager.getAuthenticatedUser())
                .thenReturn(Observable.just(user));

        when(dataManager.getUserLikes())
                .thenReturn(Observable.just(MockModel.newLikedShotList(10)));

        presenter.getAuthUser(false);

        verify(view, atLeast(1)).showLikedShots(anyListOf(Shot.class));
    }


    @Test
    public void getLikedShotSuccessfully() {
        doReturn(Observable.just(MockModel.newLikedShotList(10)))
                .when(dataManager)
                .getUserLikes();

        presenter.loadLikedShots();

        verify(view).showProgress(false);
        verify(view).showLikedShots(anyListOf(Shot.class));

    }

    @Test
    public void getLikedShotFailed() {
        doReturn(Observable.error(new RuntimeException()))
                .when(dataManager)
                .getUserLikes();

        presenter.loadLikedShots();

        verify(view).showProgress(false);
        verify(view, never()).showLikedShots(anyListOf(Shot.class));
    }

}
