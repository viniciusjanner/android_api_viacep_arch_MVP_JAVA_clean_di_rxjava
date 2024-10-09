package com.viniciusjanner.apiviacep.presentation.presenter;

import com.viniciusjanner.apiviacep.data.repository.CepRepositoryImpl;
import com.viniciusjanner.apiviacep.data.source.remote.network.ApiServiceFactory;
import com.viniciusjanner.apiviacep.data.source.remote.api.ViaCepApi;
import com.viniciusjanner.apiviacep.domain.usecase.FetchAddressUseCase;
import com.viniciusjanner.apiviacep.presentation.contract.CepContract;

public class CepFactory {

    public static CepContract.Presenter createCepPresenter() {

        ViaCepApi apiService = ApiServiceFactory.getViaCepApi();

        CepRepositoryImpl repository = new CepRepositoryImpl(apiService);

        FetchAddressUseCase fetchAddressUseCase = new FetchAddressUseCase(repository);

        return new CepPresenterImpl(fetchAddressUseCase);
    }
}
