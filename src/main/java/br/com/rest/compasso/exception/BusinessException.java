package br.com.rest.compasso.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private final transient Object[] parameters;

    public BusinessException(String code, Object... parameters) {
        super(code + " - " + Arrays.toString(parameters));

        this.code = code;
        this.parameters = parameters;
    }
}
