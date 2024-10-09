package com.viniciusjanner.apiviacep.data.source.remote.network;

import android.os.Debug;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.viniciusjanner.apiviacep.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String TAG_LOG = RetrofitClient.class.getSimpleName();

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final int TIMEOUT_SECONDS = 30;
    private static Retrofit retrofit;

    private static HttpLoggingInterceptor createLoggingInterceptor() {
        return new HttpLoggingInterceptor(
            message -> {
                if (BuildConfig.DEBUG || Debug.isDebuggerConnected()) {
                    //
                    // Verifica se a resposta é JSON e faz o pretty print
                    //
                    if (message.startsWith("{") || message.startsWith("[")) {
                        try {
                            String prettyJson = gson.toJson(JsonParser.parseString(message));
                            Log.d(TAG_LOG, prettyJson);
                        } catch (Exception e) {
                            Log.e(TAG_LOG, "Erro ao formatar JSON: " + e.getMessage());
                            Log.d(TAG_LOG, message);
                        }
                    }
                    //
                    // Ignorar conteúdo HTML e vazio para não poluir o log
                    //
                    else if (message.contains("<!DOCTYPE HTML>") || message.contains("<html")) {
                        return;
                    }
                    //
                    // Outras informações são impressas normalmente
                    //
                    else {
                        Log.d(TAG_LOG, message);
                    }
                }
            }
        ).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private static OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(createLoggingInterceptor())
            .build();
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        }
        return retrofit;
    }

    public static <API> API createService(Class<API> apiClass) {
        return getRetrofitInstance().create(apiClass);
    }
}
