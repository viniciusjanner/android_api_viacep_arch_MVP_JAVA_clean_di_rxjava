package com.viniciusjanner.apiviacep.data.repository;

import com.viniciusjanner.apiviacep.domain.model.AddressModel;

import io.reactivex.rxjava3.core.Observable;

public interface CepRepository {
    Observable<AddressModel> getAddressByCep(String cep);
}
