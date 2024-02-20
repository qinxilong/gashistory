package com.taosdata.example.springbootdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private int code;
    private String message;
    private T data;

    public Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static Response success() {
        Response response = new Response<>();
        response.setCode(200);
        response.setMessage("success");
        return response;
    }

    public static Response<?> error(int code, String message) {
        Response<?> response = new Response<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public static Response<?> fail(String message) {
        Response<?> response = new Response<>();
        response.setCode(500);
        response.setMessage(message);
        return response;
    }
}
