package com.example.demo.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public ApiResponse(int status, T data) {
        this.status = status;
        this.data = data;
        this.message = getMessageForStatus(status);
    }

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private String getMessageForStatus(int status) {
        String message;
        switch (status) {
            case 200:
                message = "OK";
                break;
            case 201:
                message = "Created";
                break;
            case 204:
                message = "No Content";
                break;
            case 400:
                message = "Bad Request";
                break;
            case 401:
                message = "Unauthorized";
                break;
            case 404:
                message = "Not Found";
                break;
            case 409:
                message = "Conflict";
                break;
            case 500:
                message = "Internal Server Error";
                break;
            default:
                message = "Unknown Status";
                break;
        }
        return message;
    }
}