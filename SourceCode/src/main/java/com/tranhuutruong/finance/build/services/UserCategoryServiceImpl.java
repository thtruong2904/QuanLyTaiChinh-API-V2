package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.entities.Budget;
import com.tranhuutruong.finance.build.entities.CategoryType;
import com.tranhuutruong.finance.build.entities.UserCategory;
import com.tranhuutruong.finance.build.entities.users.UserInformation;
import com.tranhuutruong.finance.build.repositories.BudgetRepository;
import com.tranhuutruong.finance.build.repositories.CategoryTypeRepository;
import com.tranhuutruong.finance.build.repositories.TransactionRepository;
import com.tranhuutruong.finance.build.repositories.User.UserRepository;
import com.tranhuutruong.finance.build.repositories.UserCategoryRepository;
import com.tranhuutruong.finance.build.requests.UserCategoryRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCategoryServiceImpl implements UserCategoryService{

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public ApiResponse<Object> getAllUserCategoryByUser(Long idUser)
    {
        Iterable<UserCategory> userCategoryList = userCategoryRepository.getAllByUser(idUser);
        if(!userCategoryList.iterator().hasNext()) // kiểm tra xem một iterable có rỗng hay không
        {
            return ApiResponse.builder().status(101).message("Người dùng chưa có danh mục chi tiêu").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách danh mục chi tiêu").data(userCategoryList).build();
    }

    @Override
    public ApiResponse<Object> getAllCategoryIncomeByUser(Long idUser)
    {
        Iterable<UserCategory> userCategoryIterable = userCategoryRepository.getAllUserCategoryIncome(idUser);
        if(!userCategoryIterable.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chưa có danh mục thu nhập!").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách danh mục thu nhập!").data(userCategoryIterable).build();
    }

    @Override
    public ApiResponse<Object> getAllCategoryExpenseByUser(Long idUser)
    {
        Iterable<UserCategory> userCategoryIterable = userCategoryRepository.getAllUserCategoryExpense(idUser);
        if(!userCategoryIterable.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chưa có danh mục chi tiêu!").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách danh mục chi tiêu!").data(userCategoryIterable).build();
    }

    @Override
    public ApiResponse<Object> addUserCategory(Long idUser, Long idCategoryType, UserCategoryRequest userCategoryRequest)
    {
        Optional<UserInformation> userInformation = userRepository.findById(idUser);
        if(!userInformation.isPresent())
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy người dùng").build();
        }
        Optional<CategoryType> categoryType = categoryTypeRepository.findById(idCategoryType);
        if(!categoryType.isPresent())
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy loại danh mục").build();
        }
        UserCategory userCategory = userCategoryRepository.findByUserAndCategoryTypeAndName(idUser, idCategoryType, userCategoryRequest.getName());
        if(userCategory != null)
        {
            return ApiResponse.builder().status(101).message("Tên danh mục với thể loại chi tiêu đã tồn tại").build();
        }
        UserCategory newUserCategory = UserCategory.builder()
                .userInformation(userInformation.get())
                .category(categoryType.get())
                .name(userCategoryRequest.getName())
                .color(userCategoryRequest.getColor())
                .description(userCategoryRequest.getDescription()).build();
        userCategoryRepository.save(newUserCategory);
        return ApiResponse.builder().status(200).message("Tạo danh mục chi tiêu thành công").build();
    }

    @Override
    public ApiResponse<Object> updateUserCategory(Long idUserCategory, Long idUser, Long idCategoryType, UserCategoryRequest userCategoryRequest)
    {
        UserCategory userCategory = userCategoryRepository.finByIdAndUser(idUserCategory, idUser);
        if(userCategory == null)
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy danh mục").build();
        }
        Optional<CategoryType> categoryType = categoryTypeRepository.findById(idCategoryType);
        if(!categoryType.isPresent())
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy loại danh mục").build();
        }
        UserCategory userCategoryCheck = userCategoryRepository.findByUserAndCategoryTypeAndName(idUser, idCategoryType, userCategoryRequest.getName());
        if(userCategoryCheck != null)
        {
            return ApiResponse.builder().status(101).message("Tên danh mục với thể loại chi tiêu đã tồn tại").build();
        }
        userCategory.setCategory(categoryType.get());
        if(userCategoryRequest.getName() != null)
        {
            userCategory.setName(userCategoryRequest.getName());
        }
        if(userCategoryRequest.getColor() != null)
        {
            userCategory.setColor(userCategoryRequest.getColor());
        }
        if(userCategoryRequest.getDescription() != null)
        {
            userCategory.setDescription(userCategoryRequest.getDescription());
        }
        userCategoryRepository.save(userCategory);
        return ApiResponse.builder().status(200).message("Chỉnh sửa danh mục chi tiêu thành công").build();
    }

    @Override
    public ApiResponse<Object> deleteUserCategory(Long idUserCategory, Long idUser)
    {
        UserCategory userCategory = userCategoryRepository.finByIdAndUser(idUserCategory, idUser);
        if(userCategory == null)
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy danh mục").build();
        }
        if(transactionRepository.existsByUserCategory(idUserCategory))
        {
            return ApiResponse.builder().status(101).message("Danh mục đang được sử dụng để lưu trữ giao dịch. Không được phép xóa").build();
        }
        Budget budget = budgetRepository.findByIdUserCategory(idUserCategory);
        if(budget != null)
        {
            return ApiResponse.builder().status(101).message("Danh mục đang được sử dụng cho một ngân sách. Không được xóa").build();
        }
        userCategoryRepository.delete(userCategory);
        return ApiResponse.builder().status(200).message("Xóa danh mục thành công").build();
    }

    @Override
    public ApiResponse<Object> findUserCategoryByName(Long idUser, String name)
    {
        Iterable<UserCategory> listUserCategory = userCategoryRepository.findByName(idUser, name);
        if(!listUserCategory.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Không có danh mục cần tìm kiếm").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách danh mục tìm kiếm").data(listUserCategory).build();
    }


}
