package com.viniciusjanner.apiviacep.presentation.presenter;

import com.viniciusjanner.apiviacep.domain.usecase.FetchAddressUseCase;
import com.viniciusjanner.apiviacep.domain.model.AddressModel;
import com.viniciusjanner.apiviacep.presentation.contract.CepContract;
import com.viniciusjanner.apiviacep.utils.ErrorMessage;
import com.viniciusjanner.apiviacep.utils.Utils;

import java.lang.ref.WeakReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class CepPresenterImpl implements CepContract.Presenter {

    private WeakReference<CepContract.View> viewRef;
    private final FetchAddressUseCase fetchAddressUseCase;
    private AddressModel addressModel;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public CepPresenterImpl(FetchAddressUseCase fetchAddressUseCase) {
        this.fetchAddressUseCase = fetchAddressUseCase;
    }

    @Override
    public void attachView(CepContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        viewRef.clear();
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

        disposable.add(fetchAddressUseCase.execute(cep)
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

    private void handleAddressFetchSuccess(AddressModel addressModel) {
        this.addressModel = addressModel;

        CepContract.View view = getView();

        if (view == null) return;

        view.hideLoading();
        view.displayAddress(addressModel);
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

        if (addressModel == null) {
            view.displayError(ErrorMessage.ERROR_ADDRESS_SHARE.getMessage());
            return;
        }

        Utils.shareText(addressModel.formatAddress(), view.getContext());
    }

    @Override
    public void copyAddress() {
        CepContract.View view = getView();

        if (view == null) return;

        if (addressModel == null) {
            view.displayError(ErrorMessage.ERROR_ADDRESS_COPY.getMessage());
            return;
        }

        Utils.copyTextToClipboard(addressModel.formatAddress());
    }
}
