package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.entities.TransactionType;
import com.tranhuutruong.finance.build.repositories.TransactionRepository;
import com.tranhuutruong.finance.build.repositories.TransactionTypeRepository;
import com.tranhuutruong.finance.build.requests.TransactionTypeRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionTypeServiceImpl implements TransactionTypeService{

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ApiResponse<Object> getAllTransactionType()
    {
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        return ApiResponse.builder().status(200).message("Danh sách loại giao dịch").data(transactionTypeList).build();
    }

    @Override
    public ApiResponse<Object> addTransactionType(TransactionTypeRequest transactionTypeRequest)
    {
        TransactionType transactionType = transactionTypeRepository.findByName(transactionTypeRequest.getName());
        if(transactionType != null)
        {
            return ApiResponse.builder().status(101).message("Loại giao dịch đã tồn tại").build();
        }
        transactionType = TransactionType.builder().name(transactionTypeRequest.getName()).build();
        transactionTypeRepository.save(transactionType);
        return ApiResponse.builder().status(200).message("Thêm loại giao dịch thành công").build();
    }

    @Override
    public ApiResponse<Object> updateTransactionType(Long id, TransactionTypeRequest transactionTypeRequest)
    {
        Optional<TransactionType> transactionType = transactionTypeRepository.findById(id);
        if(!transactionType.isPresent())
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy loại giao dịch").build();
        }
        transactionType.get().setName(transactionTypeRequest.getName());
        transactionTypeRepository.save(transactionType.get());
        return ApiResponse.builder().status(200).message("Thêm loại danh mục thành công").build();
    }

    @Override
    public ApiResponse<Object> deleteTransactionType(Long id)
    {
        Optional<TransactionType> transactionType = transactionTypeRepository.findById(id);
        if(!transactionType.isPresent())
        {
            return ApiResponse.builder().status(101).message("Không tìm thấy loại danh mục").build();
        }
        if(transactionRepository.existsByTransactionType(id))
        {
            return ApiResponse.builder().status(101).message("Loại giao dịch đang được sử dụng. Không được xóa!").build();
        }
        transactionTypeRepository.delete(transactionType.get());
        return ApiResponse.builder().status(200).message("Xóa loại danh mục thành công").build();
    }
}
