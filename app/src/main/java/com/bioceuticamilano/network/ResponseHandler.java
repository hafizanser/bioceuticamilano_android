package com.bioceuticamilano.network;

public interface ResponseHandler {
    <T>void onSuccess(Object response, int reqCode);
    void onFailure(Throwable t, int reqCode);
}