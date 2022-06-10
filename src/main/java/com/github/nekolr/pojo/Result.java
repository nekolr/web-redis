package com.github.nekolr.pojo;

public record Result(Object data, Boolean success, String msg) {

    public static Result success(Object data) {
        return new Result(data, Boolean.TRUE, "success");
    }

    public static Result fail(String msg) {
        return new Result(null, Boolean.FALSE, msg);
    }
}
