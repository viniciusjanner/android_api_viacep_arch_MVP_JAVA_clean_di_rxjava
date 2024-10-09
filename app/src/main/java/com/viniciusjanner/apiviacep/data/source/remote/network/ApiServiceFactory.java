package com.viniciusjanner.apiviacep.data.source.remote.network;

import com.viniciusjanner.apiviacep.data.source.remote.api.ViaCepApi;

public class ApiServiceFactory {

    public static ViaCepApi getViaCepApi() {
        return RetrofitClient.createService(ViaCepApi.class);
    }
}
