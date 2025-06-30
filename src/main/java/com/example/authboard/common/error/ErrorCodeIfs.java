package com.example.authboard.common.error;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();

    Integer getCode();

    String getDescription();


}
