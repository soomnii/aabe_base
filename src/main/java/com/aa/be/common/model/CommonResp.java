package com.aa.be.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommonResp<T> {
    private String successorNot;
    private String statusCode;
    private String errorMessage;
    private T data;

    public static <T> CommonResp<T> onSuccess(T data) {
        return CommonResp.<T>builder()
                .successorNot("SUCCESS")
                .statusCode(String.valueOf(HttpStatus.OK))
                .data(data)
                .build();
    }
}
