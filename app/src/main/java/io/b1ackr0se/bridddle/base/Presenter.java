package io.b1ackr0se.bridddle.base;

public interface Presenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}