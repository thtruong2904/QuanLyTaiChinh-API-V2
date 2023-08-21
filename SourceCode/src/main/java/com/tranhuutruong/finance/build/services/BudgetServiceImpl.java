package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.entities.Budget;
import com.tranhuutruong.finance.build.entities.UserCategory;
import com.tranhuutruong.finance.build.repositories.BudgetRepository;
import com.tranhuutruong.finance.build.repositories.UserCategoryRepository;
import com.tranhuutruong.finance.build.requests.BudgetRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;

@Service
public class BudgetServiceImpl implements BudgetService{

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    @Override
    public ApiResponse<Object> getAllBudgetByUser(Long idUser)
    {
        Iterable<Budget> budgets = budgetRepository.getAllByUser(idUser);
        if(!budgets.iterator().hasNext()){
            return ApiResponse.builder().status(101).message("Chưa có ngân sách").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách ngân sách").data(budgets).build();
    }

    @Override
    public ApiResponse<Object> addBudget(Long idUser, Long idUserCategory, BudgetRequest budgetRequest)
    {
        UserCategory userCategory = userCategoryRepository.finByIdAndUser(idUserCategory, idUser);
        if(userCategory == null)
        {
            return ApiResponse.builder().status(400).message("Không tìm thấy danh mục").build();
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        Date fromDate = new Date(cal.getTimeInMillis());
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        Date toDate = new Date(cal.getTimeInMillis());
        Budget budget = budgetRepository.findByIdUserCategoryAndFromDateAndToDate(userCategory.getId(), fromDate, toDate);
        if(budget != null)
        {
            return ApiResponse.builder().status(101).message("Ngân sách với danh mục chi tiêu trong tháng đã tồn tại").build();
        }
        Budget newBudget = Budget.builder().userCategory(userCategory).amount(budgetRequest.getAmount())
                .fromDate(fromDate).toDate(toDate).description(budgetRequest.getDescription()).build();
        budgetRepository.save(newBudget);
        return ApiResponse.builder().status(200).message("Tạo ngân sách thành công!").build();
    }

    @Override
    public ApiResponse<Object> updateBudget(Long idBudget, Long idUser, BudgetRequest budgetRequest)
    {
        Budget budget = budgetRepository.findByIdAndUser(idBudget, idUser);
        if(budget == null)
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy ngân sách").build();
        }
//        Budget budgetCheck = budgetRepository.findByIdUserCategoryAndFromDateAndToDate(userCategory.getId(), fromDate, toDate);
//        if(budgetCheck!= null)
//        {
//            return ApiResponse.builder().status(101).message("Ngân sách với danh mục chi tiêu trong tháng đã tồn tại").build();
//        }
        else {
            if(budgetRequest.getAmount() > 0)
            {
                budget.setAmount(budgetRequest.getAmount());
            }
            if(budgetRequest.getDescription() != null)
            {
                budget.setDescription(budgetRequest.getDescription());
            }
            budgetRepository.save(budget);
        }
        return ApiResponse.builder().status(200).message("Sửa ngân sách thành công").build();
    }

    @Override
    public ApiResponse<Object> deleteBudget(Long idBudget, Long idUser)
    {
        Budget budget = budgetRepository.findByIdAndUser(idBudget, idUser);
        if(budget == null)
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy ngân sách").build();
        }
        budgetRepository.delete(budget);
        return ApiResponse.builder().status(200).message("Xóa ngân sách thành công").build();
    }

    @Override
    public ApiResponse<Object> findBudgetByName(Long idUser, String name)
    {
        Iterable<Budget> listBudget = budgetRepository.findByName(idUser, name);
        if(!listBudget.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Không có ngân sách cần tìm kiếm").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách ngân sách tìm kiếm").data(listBudget).build();
    }
}
