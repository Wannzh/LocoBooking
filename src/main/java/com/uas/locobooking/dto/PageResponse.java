package com.uas.locobooking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PageResponse<T> {
    private int page;
    private int size;
    private int totalPage;
    private long totalItem;
    private List<T> items;
}
