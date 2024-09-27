package com.viniciusjanner.apiviacep.utils;

public enum ErrorMessage {

    ERROR_ADDRESS_INVALID("CEP inválido."),
    ERROR_ADDRESS_FETCH("Erro ao buscar o endereço."),
    ERROR_ADDRESS_NOT_FOUND("CEP não encontrado."),
    ERROR_ADDRESS_SHARE("Endereço não disponível para compartilhar."),
    ERROR_ADDRESS_COPY("Endereço não disponível para copiar.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
