package com.viniciusjanner.apiviacep.presenter;

public class CepFactory {

    public static CepContract.Presenter createCepPresenter() {
        return new CepPresenterImpl();
    }
}
