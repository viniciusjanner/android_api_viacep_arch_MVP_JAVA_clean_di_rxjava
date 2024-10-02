package com.viniciusjanner.apiviacep.presenter;

import com.viniciusjanner.apiviacep.api.RetrofitClient;
import com.viniciusjanner.apiviacep.api.ViaCepApi;
import com.viniciusjanner.apiviacep.model.Address;
import com.viniciusjanner.apiviacep.utils.ErrorMessage;
import com.viniciusjanner.apiviacep.utils.Utils;

import java.lang.ref.WeakReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CepPresenterImpl implements CepContract.Presenter {

    private WeakReference<CepContract.View> viewRef;
    private ViaCepApi apiService;
    private Address address;
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void attachView(CepContract.View view) {
        this.apiService = RetrofitClient.createService(ViaCepApi.class);
        this.viewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.viewRef.clear();
        disposable.clear();
    }

    private CepContract.View getView() {
        return viewRef.get();
    }

    @Override
    public void fetchAddress(String cep) {
        CepContract.View view = getView();

        if (view == null) return;

        if (!Utils.isValidCep(cep)) {
            view.displayError(ErrorMessage.ERROR_ADDRESS_INVALID.getMessage());
            return;
        }

        view.showLoading();
        disposable.add(apiService.fetchAddress(cep)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                address -> {
                    //
                    // A api viacep retorna apenas error = true no json caso o cep não seja encontrado
                    //
                    if (!address.isErro()) {
                        handleAddressFetchSuccess(address);
                    } else {
                        view.hideLoading();
                        view.displayError(ErrorMessage.ERROR_ADDRESS_NOT_FOUND.getMessage());
                    }
                },
                throwable -> handleError(ErrorMessage.ERROR_ADDRESS_FETCH.getMessage())
            ));
    }

    private void handleAddressFetchSuccess(Address address) {
        this.address = address;

        CepContract.View view = getView();

        if (view == null) return;

        view.hideLoading();
        view.displayAddress(address);
    }

    private void handleError(String errorMessage) {
        CepContract.View view = getView();

        if (view == null) return;

        view.hideLoading();
        view.displayError(errorMessage);
    }

    @Override
    public void shareAddress() {
        CepContract.View view = getView();

        if (view == null) return;

        if (address == null) {
            view.displayError(ErrorMessage.ERROR_ADDRESS_SHARE.getMessage());
            return;
        }

        Utils.shareText(address.formatAddress(), view.getContext());
    }

    @Override
    public void copyAddress() {
        CepContract.View view = getView();

        if (view == null) return;

        if (address == null) {
            view.displayError(ErrorMessage.ERROR_ADDRESS_COPY.getMessage());
            return;
        }

        Utils.copyTextToClipboard(address.formatAddress());
    }
}
