package com.tranhuutruong.finance.build.controllers;

import com.tranhuutruong.finance.build.requests.TransactionRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import com.tranhuutruong.finance.build.security.UserPrinciple.UserPrincipal;
import com.tranhuutruong.finance.build.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping(value = "/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/family/{month}/{year}")
    public ApiResponse<Object> getTransactionFamily(UserPrincipal userPrincipal, @PathVariable("month") Integer month, @PathVariable("year") Integer year)
    {
        return transactionService.getTransactionFamilyInMonth(userPrincipal.getUserId(), month, year);
    }

    @GetMapping(value = "/personal/{month}/{year}")
    public ApiResponse<Object> getTransactionPersonal(UserPrincipal userPrincipal, @PathVariable("month") Integer month, @PathVariable("year") Integer year)
    {
        return transactionService.getTransactionPersonalInMonth(userPrincipal.getUserId(), month, year);
    }

    @PostMapping(value = "/add-personal/{idUserCategory}")
    public ApiResponse<Object> addTransactionPersonal(@PathVariable("idUserCategory") Long idUserCategory, @RequestBody TransactionRequest transactionRequest)
    {
        return transactionService.addTransactionPersonal(idUserCategory, transactionRequest);
    }

    @PostMapping(value = "/add-family/{idUserCategory}")
    public ApiResponse<Object> addTransactionFamily(@PathVariable("idUserCategory") Long idUserCategory, @RequestBody TransactionRequest transactionRequest)
    {
        return transactionService.addTransactionFamily(idUserCategory, transactionRequest);
    }

    @PutMapping(value = "/update/{idTran}")
    public ApiResponse<Object> updateTransaction(UserPrincipal userPrincipal, @PathVariable("idTran") Long idTran, @RequestBody TransactionRequest transactionRequest)
    {
        return transactionService.updateTransaction(idTran, userPrincipal.getUserId(), transactionRequest);
    }

    @DeleteMapping(value = "/delete/{idTran}")
    public ApiResponse<Object> deleteTransaction(UserPrincipal userPrincipal, @PathVariable("idTran") Long idTran)
    {
        return transactionService.deleteTransaction(idTran, userPrincipal.getUserId());
    }


    // tổng thu nhập và chi tiêu của 1 tháng trong 1 năm
    @GetMapping(value = "/list-income/year/{year}")
    public ApiResponse<Object> listIncomeInYear(UserPrincipal userPrincipal, @PathVariable("year") Integer year)
    {
        return transactionService.getTotalIncomeInYear(userPrincipal.getUserId(), year);
    }

    @GetMapping(value = "/list-expense/year/{year}")
    public ApiResponse<Object> listExpenseInYear(UserPrincipal userPrincipal, @PathVariable("year") Integer year)
    {
        return transactionService.getTotalExpenseInYear(userPrincipal.getUserId(), year);
    }

    // tổng chi tiêu của gia đình qua các tháng trong 1 năm
    @GetMapping(value = "/list-expense-family/year/{year}")
    public ApiResponse<Object> listExpenseFamilyInYear(UserPrincipal userPrincipal, @PathVariable("year") Integer year)
    {
        return transactionService.getTotalExpenseFamilyInYear(userPrincipal.getUserId(), year);
    }

    @GetMapping(value = "/income-expense-personal-and-expense-family/{month}/{year}")
    public ApiResponse<Object> getTotalIncomeAndExpensePersonalAndExpenseFamilyInMonth(UserPrincipal userPrincipal, @PathVariable("month") Integer month, @PathVariable("year") Integer year)
    {
        return transactionService.getTotalIncomeAndExpensePersonalAndExpenseFamilyInMonth(userPrincipal.getUserId(), month, year);
    }

    @GetMapping(value = "/total-income-expense-personal-and-expense-family/{year}")
    public ApiResponse<Object> getTotalIncomeAndExpensePersonalAndExpenseFamilyInYear(UserPrincipal userPrincipal, @PathVariable("year") Integer year)
    {
        return transactionService.getTotalIncomeAndExpensePersonalAndExpenseFamilyInYear(userPrincipal.getUserId(), year);
    }

    // lấy tất cả giao dịch thu nhập cá nhân trong 1 tháng
    @GetMapping(value = "/all-income-personal-month/{month}/{year}")
    public ApiResponse<Object> getAllTransactionIncomePersonalInMonth(UserPrincipal userPrincipal, @PathVariable("month") Integer month, @PathVariable("year") Integer year)
    {
        return transactionService.getAllTransactionIncomePersonalInMonth(userPrincipal.getUserId(), month, year);
    }

    @GetMapping(value = "/all-expense-personal-month/{month}/{year}")
    public ApiResponse<Object> getAllTransactionExpensePersonalInMonth(UserPrincipal userPrincipal, @PathVariable("month") Integer month, @PathVariable("year") Integer year)
    {
        return transactionService.getAllTransactionExpensePersonalInMonth(userPrincipal.getUserId(), month, year);
    }

    @GetMapping(value = "/all-expense-family-month/{month}/{year}")
    public ApiResponse<Object> getAllTransactionExpenseFamilyInMonth(UserPrincipal userPrincipal, @PathVariable("month") Integer month, @PathVariable("year") Integer year)
    {
        return transactionService.getAllTransactionExpenseFamilyInMonth(userPrincipal.getUserId(), month, year);
    }

    @GetMapping(value = "/all/timerange/{startDate}/{endDate}")
    public ApiResponse<Object> getAllTransactionByTimeRange(UserPrincipal userPrincipal, @PathVariable("startDate") Date startDate, @PathVariable("endDate") Date endDate)
    {
        return transactionService.getAllByTimeRange(userPrincipal.getUserId(), startDate, endDate);
    }

    // lấy tất cả giao dịch trong 1 tháng trong 1 năm
    @GetMapping(value = "/all/month/{month}/year/{year}")
    public ApiResponse<Object> getAllTransactionInMonth(UserPrincipal userPrincipal, @PathVariable("month") Integer month, @PathVariable("year") Integer year)
    {
        return transactionService.getAllTransactionInMonth(userPrincipal.getUserId(), month, year);
    }

    @GetMapping(value = "/all/year/{year}")
    public ApiResponse<Object> getAllTransactionInYear(UserPrincipal userPrincipal, @PathVariable("year") Integer year)
    {
        return transactionService.getAllTransactionInYear(userPrincipal.getUserId(), year);
    }

    @GetMapping(value = "/total/by/user-category/{idUserCategory}")
    public ApiResponse<Object> getTotalByUserCategory(UserPrincipal userPrincipal, @PathVariable("idUserCategory") Long idUserCategory)
    {
        return transactionService.getTotalByUserCategory(userPrincipal.getUserId(), idUserCategory);
    }

    @GetMapping(value = "/search-family")
    public ApiResponse<Object> findByNameAndFamily(UserPrincipal userPrincipal, @RequestParam(name = "name", required = false) String name)
    {
        return transactionService.findTransactionByNameAndFamily(userPrincipal.getUserId(), name);
    }

    @GetMapping(value = "/search-personal-income")
    public ApiResponse<Object> findByNameTransactionIncomeAndPersonal(UserPrincipal userPrincipal, @RequestParam(name = "name", required = false) String name)
    {
        return transactionService.findTransactionIncomeByNameAndPersonal(userPrincipal.getUserId(), name);
    }

    @GetMapping(value = "/search-personal-expense")
    public ApiResponse<Object> findByNameTransactionExpenseAndPersonal(UserPrincipal userPrincipal, @RequestParam(name = "name", required = false) String name)
    {
        return transactionService.findTransactionExpenseByNameAndPersonal(userPrincipal.getUserId(), name);
    }

    // lấy ra các giao dịch gần đây
    @GetMapping(value = "/10-income-personal-nearest")
    public ApiResponse<Object> get10TransactionPersonalIncomeNearest(UserPrincipal userPrincipal)
    {
        return transactionService.get10TransactionPersonalIncomeNearest(userPrincipal.getUserId());
    }

    @GetMapping(value = "/10-expense-personal-nearest")
    public ApiResponse<Object> get10TransactionPersonalExpenseNearest(UserPrincipal userPrincipal)
    {
        return transactionService.get10TransactionPersonalExpenseNearest(userPrincipal.getUserId());
    }

    @GetMapping(value = "/5-expense-family-nearest")
    public ApiResponse<Object> get5TransactionFamilyExpenseNearest(UserPrincipal userPrincipal)
    {
        return transactionService.get5TransactionFamilyExpenseNearest(userPrincipal.getUserId());
    }

    @GetMapping(value = "/income-personal-timerange/{startDate}/{endDate}")
    public ApiResponse<Object> getAllTransactionPersonalIncomeByTimeRange(UserPrincipal userPrincipal, @PathVariable("startDate") Date startDate, @PathVariable("endDate") Date endDate)
    {
        return transactionService.getAllTransactionPersonalIncomeByTimeRange(userPrincipal.getUserId(), startDate, endDate);
    }

    @GetMapping(value = "/expense-personal-timerange/{startDate}/{endDate}")
    public ApiResponse<Object> getAllTransactionPersonalExpenseByTimeRange(UserPrincipal userPrincipal, @PathVariable("startDate") Date startDate, @PathVariable("endDate") Date endDate)
    {
        return transactionService.getAllTransactionPersonalExpenseByTimeRange(userPrincipal.getUserId(), startDate, endDate);
    }
}
