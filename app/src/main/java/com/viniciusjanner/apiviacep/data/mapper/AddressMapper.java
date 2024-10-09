package com.viniciusjanner.apiviacep.data.mapper;

import com.viniciusjanner.apiviacep.data.source.remote.response.AddressResponse;
import com.viniciusjanner.apiviacep.domain.model.AddressModel;

@SuppressWarnings("unused")
public class AddressMapper {

    public static AddressModel toDomain(AddressResponse response) {
        return new AddressModel(
            response.getCep(),
            response.getLogradouro(),
            response.getComplemento(),
            response.getUnidade(),
            response.getBairro(),
            response.getLocalidade(),
            response.getUf(),
            response.getEstado(),
            response.getRegiao(),
            response.getIbge(),
            response.getGia(),
            response.getDdd(),
            response.getSiafi(),
            response.isErro()
        );
    }
}
