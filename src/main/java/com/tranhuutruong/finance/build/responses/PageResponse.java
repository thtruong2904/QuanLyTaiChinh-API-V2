package com.tranhuutruong.finance.build.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResponse<T> {
    private T data;

    private Integer numberPage;

    private Integer totalElements;

    private Integer totalPages;
}
