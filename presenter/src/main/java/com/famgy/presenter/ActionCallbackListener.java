package com.famgy.presenter;

/**
 * Created by famgy on 3/7/18.
 */

public interface ActionCallbackListener<T> {

    public void onSuccess(T data);

    public void onFailure(String errorEvent, String message);
}
