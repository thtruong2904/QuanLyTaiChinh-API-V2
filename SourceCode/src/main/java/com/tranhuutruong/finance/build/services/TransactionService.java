package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.requests.TransactionRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;

import java.sql.Date;

public interface TransactionService {
    public ApiResponse<Object> addTransactionPersonal(Long idUserCategory, TransactionRequest transactionRequest);

    public ApiResponse<Object> addTransactionFamily(Long idUserCategory, TransactionRequest transactionRequest);

    public ApiResponse<Object> getTransactionPersonalInMonth(Long idUser, Integer month, Integer year);

    public ApiResponse<Object> getTransactionFamilyInMonth(Long idUser, Integer month, Integer year);

    public ApiResponse<Object> updateTransaction(Long idTransaction, Long idUser, TransactionRequest transactionRequest);

    public ApiResponse<Object> deleteTransaction(Long idTransaction, Long idUser);

    // tổng thu nhập và chi tiêu qua các tháng trong 1 năm
    public ApiResponse<Object> getTotalIncomeInYear(Long idUser, Integer year);

    public ApiResponse<Object> getTotalExpenseInYear(Long idUser, Integer year);

    public ApiResponse<Object> getTotalExpenseFamilyInYear(Long idUser, Integer year);

    public ApiResponse<Object> getTotalIncomeAndExpensePersonalAndExpenseFamilyInMonth(Long idUser, Integer month, Integer year);

    public ApiResponse<Object> getTotalIncomeAndExpensePersonalAndExpenseFamilyInYear(Long idUser, Integer year);
    // lấy tất cả giao dịch thu nhập cá nhân trong 1 tháng
    public ApiResponse<Object> getAllTransactionIncomePersonalInMonth(Long idUser, Integer month, Integer year);

    // lấy tất cả giao dịch chi tiêu cá nhân trong 1 tháng
    public ApiResponse<Object> getAllTransactionExpensePersonalInMonth(Long idUser, Integer month, Integer year);

    // lấy tất cả giao dịch chi tiêu gia đình trong 1 tháng
    public ApiResponse<Object> getAllTransactionExpenseFamilyInMonth(Long idUser, Integer month, Integer year);

    // lất tất cả giao dịch trong 1 khoảng thời gian
    public ApiResponse<Object> getAllByTimeRange(Long idUser, Date startDate, Date endDate);

    // lấy tất giao dịch trong 1 tháng trong 1 năm
    public ApiResponse<Object> getAllTransactionInMonth(Long idUser, Integer month, Integer year);

    // lấy tất cả giao dịch trong 1 năm
    public ApiResponse<Object> getAllTransactionInYear(Long idUser, Integer year);

    public ApiResponse<Object> getTotalByUserCategory(Long idUser, Long idUserCategory);

    public ApiResponse<Object> findTransactionByNameAndFamily(Long idUser, String name);

    public ApiResponse<Object> findTransactionIncomeByNameAndPersonal(Long idUser, String name);

    public ApiResponse<Object> findTransactionExpenseByNameAndPersonal(Long idUser, String name);

    public ApiResponse<Object> get10TransactionPersonalIncomeNearest(Long idUser);

    public ApiResponse<Object> get10TransactionPersonalExpenseNearest(Long idUser);

    public ApiResponse<Object> get5TransactionFamilyExpenseNearest(Long idUser);

    public ApiResponse<Object> getAllTransactionPersonalIncomeByTimeRange(Long idUser, Date startDate, Date endDate);

    public ApiResponse<Object> getAllTransactionPersonalExpenseByTimeRange(Long idUser, Date startDate, Date endDate);
}
