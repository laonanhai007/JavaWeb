package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestBean<T> {
    Integer statusCode;
    Boolean success;
    T message;

    public static <T> RestBean success(T message) throws IOException {
        return new RestBean(200, true, message);
    }

    public static <T> RestBean success() throws IOException {
        return new RestBean(200, true, null);
    }


    public static <T> RestBean failure(Integer statusCode, T message) throws IOException {
        return new RestBean(statusCode, false, message);
    }


}
