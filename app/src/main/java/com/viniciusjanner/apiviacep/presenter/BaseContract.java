package com.viniciusjanner.apiviacep.presenter;

public interface BaseContract {

    interface View {}

    interface Presenter<V extends View> {

        void attachView(V view);

        void detachView();
    }
}
