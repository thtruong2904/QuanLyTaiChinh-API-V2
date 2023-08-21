package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.requests.TransactionTypeRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;

public interface TransactionTypeService {
    public ApiResponse<Object> getAllTransactionType();

    public ApiResponse<Object> addTransactionType(TransactionTypeRequest transactionTypeRequest);

    public ApiResponse<Object> updateTransactionType(Long id, TransactionTypeRequest transactionTypeRequest);

    public ApiResponse<Object> deleteTransactionType(Long id);
}
