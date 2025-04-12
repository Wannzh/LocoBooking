package com.uas.locobooking.dto;

import com.javaproject.locobooking.constants.MessageConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GeneralResponse<T> {

    private boolean success;
    private String responseMessage;
    private T data;

    public static <T> GeneralResponse<T> empty() {
        return success(null, MessageConstant.OK_EMPTY);
    }

    public static <T> GeneralResponse<T> success(T data, String responseMessage) {

        return GeneralResponse.<T>builder().responseMessage(responseMessage).data(data).success(true).build();
    }

    public static <T> GeneralResponse<T> error(String responseMessage) {

        return GeneralResponse.<T>builder().responseMessage(responseMessage).success(false).build();
    }

}
