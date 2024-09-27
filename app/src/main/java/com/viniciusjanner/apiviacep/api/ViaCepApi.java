package com.viniciusjanner.apiviacep.api;

import com.viniciusjanner.apiviacep.model.Address;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepApi {

    @GET("{cep}/json/")
    Call<Address> fetchAddress(@Path("cep") String cep);
}
