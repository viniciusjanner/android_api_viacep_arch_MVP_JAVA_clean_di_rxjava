package com.viniciusjanner.apiviacep.presentation.contract;

public interface BaseContract {

    interface View {}

    interface Presenter<V extends View> {

        void attachView(V view);

        void detachView();
    }
}
