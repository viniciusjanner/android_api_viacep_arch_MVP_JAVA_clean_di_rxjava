package com.viniciusjanner.apiviacep.data.source.remote.api;

import com.viniciusjanner.apiviacep.data.source.remote.response.AddressResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepApi {

    @GET("{cep}/json/")
    Observable<AddressResponse> fetchAddress(@Path("cep") String cep);
}
