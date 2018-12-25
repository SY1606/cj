package com.example.yuekao8.core;

import com.example.yuekao8.bean.Result;

public interface DataCall<T> {
    void success(T data);
    void fail(Result result);
}
