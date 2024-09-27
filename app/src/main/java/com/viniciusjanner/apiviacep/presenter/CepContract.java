package com.viniciusjanner.apiviacep.presenter;

import android.content.Context;

import com.viniciusjanner.apiviacep.model.Address;

public interface CepContract {

    interface View extends BaseContract.View {

        Context getContext();

        void showLoading();

        void hideLoading();

        void displayAddress(Address address);

        void displayError(String message);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void fetchAddress(String cep);

        void shareAddress();

        void copyAddress();
    }
}
