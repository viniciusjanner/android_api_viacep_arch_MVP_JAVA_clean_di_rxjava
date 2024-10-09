package com.viniciusjanner.apiviacep.presentation.contract;

import android.content.Context;

import com.viniciusjanner.apiviacep.domain.model.AddressModel;

public interface CepContract {

    interface View extends BaseContract.View {

        Context getContext();

        void showLoading();

        void hideLoading();

        void displayAddress(AddressModel addressModel);

        void displayError(String message);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void fetchAddress(String cep);

        void shareAddress();

        void copyAddress();
    }
}
