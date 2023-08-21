package com.tranhuutruong.finance.build.controllers;

import com.tranhuutruong.finance.build.requests.TransactionTypeRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.services.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/transaction-type")
public class TransactionTypeController {

    @Autowired
    private TransactionTypeService transactionTypeService;

    @GetMapping(value = "/all")
    public ApiResponse<Object> getAllTransactionType()
    {
        return transactionTypeService.getAllTransactionType();
    }

    @PostMapping(value = "/add")
    public ApiResponse<Object> addCategory(@RequestBody TransactionTypeRequest transactionTypeRequest)
    {
        return transactionTypeService.addTransactionType(transactionTypeRequest);
    }

    @PutMapping(value = "/update/{id}")
    public ApiResponse<Object> updateTransactionType(@PathVariable("id") Long id, @RequestBody TransactionTypeRequest transactionTypeRequest)
    {
        return transactionTypeService.updateTransactionType(id, transactionTypeRequest);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponse<Object> deleteTransactionType(@PathVariable("id") Long id)
    {
        return transactionTypeService.deleteTransactionType(id);
    }
}
