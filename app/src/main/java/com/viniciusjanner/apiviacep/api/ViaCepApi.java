package com.viniciusjanner.apiviacep.api;

import com.viniciusjanner.apiviacep.model.Address;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepApi {

    @GET("{cep}/json/")
    Observable<Address> fetchAddress(@Path("cep") String cep);
}
