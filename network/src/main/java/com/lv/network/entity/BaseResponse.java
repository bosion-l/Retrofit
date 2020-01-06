package com.lv.network.entity;

/**
 * 根据实际返回而定
 *
 * @param <T>
 */
public class BaseResponse<T> {
    private boolean error;
    private T results;


    public T getResult() {
        return results;
    }

    public void setResult(T result) {
        this.results = result;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
