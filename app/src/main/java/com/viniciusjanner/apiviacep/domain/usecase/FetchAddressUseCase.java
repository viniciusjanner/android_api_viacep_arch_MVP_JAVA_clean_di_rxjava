package com.viniciusjanner.apiviacep.domain.usecase;

import com.viniciusjanner.apiviacep.domain.model.AddressModel;
import com.viniciusjanner.apiviacep.data.repository.CepRepository;

import io.reactivex.rxjava3.core.Observable;

public class FetchAddressUseCase implements BaseUseCase<AddressModel, String> {

    private final CepRepository repository;

    public FetchAddressUseCase(CepRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<AddressModel> execute(String cep) {
        return repository.getAddressByCep(cep);
    }
}
