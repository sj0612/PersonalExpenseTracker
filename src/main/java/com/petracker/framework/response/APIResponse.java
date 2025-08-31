package com.petracker.framework.response;


public class APIResponse {
    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    private final String message;
    private final Object data;
    public APIResponse(String message , Object data) {
        this.data = data;
        this.message = message;
    }
}
