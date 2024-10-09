package com.viniciusjanner.apiviacep.data.repository;

import com.viniciusjanner.apiviacep.data.mapper.AddressMapper;
import com.viniciusjanner.apiviacep.data.source.remote.api.ViaCepApi;
import com.viniciusjanner.apiviacep.domain.model.AddressModel;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CepRepositoryImpl implements CepRepository {

    private final ViaCepApi apiService;

    public CepRepositoryImpl(ViaCepApi apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<AddressModel> getAddressByCep(String cep) {
        return apiService.fetchAddress(cep)
            .subscribeOn(Schedulers.io()) // Executar em thread separada
            .map(AddressMapper::toDomain); // Converter a resposta da API para o modelo de domínio
    }
}
