package com.viniciusjanner.apiviacep.presenter;

import androidx.annotation.NonNull;

import com.viniciusjanner.apiviacep.api.RetrofitClient;
import com.viniciusjanner.apiviacep.api.ViaCepApi;
import com.viniciusjanner.apiviacep.model.Address;
import com.viniciusjanner.apiviacep.utils.ErrorMessage;
import com.viniciusjanner.apiviacep.utils.Utils;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CepPresenterImpl implements CepContract.Presenter {

    private WeakReference<CepContract.View> viewRef;
    private ViaCepApi apiService;
    private Address address;

    @Override
    public void attachView(CepContract.View view) {
        this.apiService = RetrofitClient.createService(ViaCepApi.class);
        this.viewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.viewRef.clear();
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
        Call<Address> call = apiService.fetchAddress(cep);
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                view.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    Address address = response.body();
                    //
                    // A api viacep retorna apenas error = true no json caso o cep não seja encontrado
                    //
                    if (!address.isErro()) {
                        handleAddressFetchSuccess(address);
                    } else {
                        view.displayError(ErrorMessage.ERROR_ADDRESS_NOT_FOUND.getMessage());
                    }
                } else {
                    view.displayError(ErrorMessage.ERROR_ADDRESS_FETCH.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Address> call, @NonNull Throwable t) {
                view.hideLoading();
                handleError(ErrorMessage.ERROR_ADDRESS_FETCH.getMessage());
            }
        });
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
