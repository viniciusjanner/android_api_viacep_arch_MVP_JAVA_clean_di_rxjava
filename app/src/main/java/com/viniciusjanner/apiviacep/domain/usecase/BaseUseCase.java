package com.viniciusjanner.apiviacep.domain.usecase;

import io.reactivex.rxjava3.core.Observable;

public interface BaseUseCase<T, Params> {
    Observable<T> execute(Params params);
}
