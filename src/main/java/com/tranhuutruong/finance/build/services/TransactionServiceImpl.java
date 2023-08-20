package com.tranhuutruong.finance.build.services;

import com.tranhuutruong.finance.build.entities.Transactions;
import com.tranhuutruong.finance.build.entities.TransactionType;
import com.tranhuutruong.finance.build.entities.UserCategory;
import com.tranhuutruong.finance.build.repositories.TransactionRepository;
import com.tranhuutruong.finance.build.repositories.TransactionTypeRepository;
import com.tranhuutruong.finance.build.repositories.UserCategoryRepository;
import com.tranhuutruong.finance.build.requests.TransactionRequest;
import com.tranhuutruong.finance.build.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Override
    public ApiResponse<Object> getTransactionFamilyInMonth(Long idUser, Integer month, Integer year) {
        Iterable<Transactions> listTranFamily = transactionRepository.findTransactionsByTransactionTypeAndMonthAndUserIdFamily(month, year, idUser);
        if(!listTranFamily.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chưa có giao dịch gia đình trong tháng").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch").data(listTranFamily).build();
    }

    @Override
    public ApiResponse<Object> getTransactionPersonalInMonth(Long idUser, Integer month, Integer year) {
        Iterable<Transactions> listTranPersonal = transactionRepository.findTransactionsByTransactionTypeAndMonthAndUserIdPersonal(month, year, idUser);
        if(!listTranPersonal.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chưa có giao dịch cá nhân trong tháng").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch").data(listTranPersonal).build();
    }

    @Override
    public ApiResponse<Object> addTransactionPersonal(Long idUserCategory, TransactionRequest transactionRequest) {
        Optional<TransactionType> transactionType = transactionTypeRepository.findById(2L);
        Optional<UserCategory> userCategory = userCategoryRepository.findById(idUserCategory);
        Transactions transaction = Transactions.builder().transactionType(transactionType.get())
                .userCategory(userCategory.get()).name(transactionRequest.getName())
                .amount(transactionRequest.getAmount()).location(transactionRequest.getLocation())
                .date(transactionRequest.getDate()).description(transactionRequest.getDescription()).build();
        transactionRepository.save(transaction);
        return ApiResponse.builder().status(200).message("Thêm giao dịch cá nhân thành công").build();
    }

    @Override
    public ApiResponse<Object> addTransactionFamily(Long idUserCategory, TransactionRequest transactionRequest) {
        Optional<TransactionType> transactionType = transactionTypeRepository.findById(1L);
        Optional<UserCategory> userCategory = userCategoryRepository.findById(idUserCategory);
        Transactions transaction = Transactions.builder().transactionType(transactionType.get())
                .userCategory(userCategory.get()).name(transactionRequest.getName())
                .amount(transactionRequest.getAmount()).location(transactionRequest.getLocation())
                .date(transactionRequest.getDate()).description(transactionRequest.getDescription()).build();
        transactionRepository.save(transaction);
        return ApiResponse.builder().status(200).message("Thêm giao dịch gia đình thành công").build();
    }

    @Override
    public ApiResponse<Object> updateTransaction(Long idTransaction, Long idUser, TransactionRequest transactionRequest)
    {
        Transactions transaction = transactionRepository.findByIdAndUser(idTransaction, idUser);
        if(transaction == null)
        {
            return ApiResponse.builder().status(101).message("Không tìm thấy giao dịch").build();
        }
        if (transactionRequest.getName() != null) transaction.setName(transactionRequest.getName());
        if (transactionRequest.getAmount() != null) transaction.setAmount(transactionRequest.getAmount());
        if (transactionRequest.getDate() != null) transaction.setDate(transactionRequest.getDate());
        if (transactionRequest.getLocation() != null) transaction.setLocation(transactionRequest.getLocation());
        if (transactionRequest.getDescription() != null) transaction.setDescription(transactionRequest.getDescription());
        transactionRepository.save(transaction);
        return ApiResponse.builder().status(200).message("Sửa giao dịch thành công").build();
    }

    @Override
    public ApiResponse<Object> deleteTransaction(Long idTransaction, Long idUser)
    {
        Transactions transaction = transactionRepository.findByIdAndUser(idTransaction, idUser);
        if(transaction == null)
        {
            return ApiResponse.builder().status(404).message("Không tìm thấy giao dịch").build();
        }
        transactionRepository.delete(transaction);
        return ApiResponse.builder().status(200).message("Xóa giao dịch thành công!").build();
    }


    // lấy tổng thu nhập qua các tháng trong năm
    private Long getTotalIncomeWithMonth(Long idUser, Integer year, int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        Date fromDate = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date toDate = new Date(calendar.getTimeInMillis());

        Long totalIncome = transactionRepository.getTotalIncomeInStartDateAndEndDate(idUser, fromDate, toDate, year);
        if(totalIncome == null)
        {
            return 0L;
        }
        else
            return totalIncome;
    }

    @Override
    public ApiResponse<Object> getTotalIncomeInYear(Long idUser, Integer year)
    {
        List<Long> listTotalIncomeWithYear = new ArrayList<>();
        for(int i = 1; i <= 12; i++)
        {
            Long total = getTotalIncomeWithMonth(idUser, year, i);
            listTotalIncomeWithYear.add(total);
        }
        return ApiResponse.builder().status(200).message("Tổng thu nhập qua các tháng").data(listTotalIncomeWithYear).build();
    }

    // lấy tổng chi tiêu qua các tháng trong năm
    private Long getTotalExpenseWithMonth(Long idUser, Integer year, int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        Date fromDate = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date toDate = new Date(calendar.getTimeInMillis());

        Long totalExpense = transactionRepository.getTotalExpenseInStartDateAndEndDate(idUser, fromDate, toDate, year);
        if(totalExpense == null)
        {
            return 0L;
        }
        else
            return totalExpense;
    }

    @Override
    public ApiResponse<Object> getTotalExpenseInYear(Long idUser, Integer year)
    {
        List<Long> listTotalExpenseWithYear = new ArrayList<>();
        for(int i = 1; i <= 12; i++)
        {
            Long total = getTotalExpenseWithMonth(idUser, year, i);
            listTotalExpenseWithYear.add(total);
        }
        return ApiResponse.builder().status(200).message("Tổng chi tiêu qua các tháng").data(listTotalExpenseWithYear).build();
    }

    // chi tiêu của gia đình qua các tháng
    private Long getTotalExpenseFamilyInMonth(Long idUser, Integer year, int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        Date fromDate = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date toDate = new Date(calendar.getTimeInMillis());

        Long totalExpense = transactionRepository.getTotalExpenseFamilyInStartDateAndEndDate(idUser, fromDate, toDate, year);
        if(totalExpense == null)
        {
            return 0L;
        }
        else
            return totalExpense;
    }

    @Override
    public ApiResponse<Object> getTotalExpenseFamilyInYear(Long idUser, Integer year)
    {
        List<Long> listTotalExpenseFamilyWithYear = new ArrayList<>();
        for(int i = 1; i <= 12; i++)
        {
            Long total = getTotalExpenseFamilyInMonth(idUser, year, i);
            listTotalExpenseFamilyWithYear.add(total);
        }
        return ApiResponse.builder().status(200).message("Tổng chi tiêu gia đình qua các tháng").data(listTotalExpenseFamilyWithYear).build();
    }

    // lấy thu nhập cá nhân -> chi tiêu các nhân -> chi tiêu gia đình trong 1 thag trong 1 năm
    @Override
    public ApiResponse<Object> getTotalIncomeAndExpensePersonalAndExpenseFamilyInMonth(Long idUser, Integer month, Integer year)
    {
        List<Long> listTotalIncomeAndExpensePersonalAndExpenseFamilyInMonth = new ArrayList<>();

        Long totalIncomePersonal = transactionRepository.getTotalIncomePersonalInMonth(idUser, month, year);
        Long totalExpensePersonal = transactionRepository.getTotalExpensePersonalInMonth(idUser, month, year);
        Long totalExpenseFamily = transactionRepository.getTotalExpenseFamilyInMonth(idUser, month, year);

        listTotalIncomeAndExpensePersonalAndExpenseFamilyInMonth.add(totalIncomePersonal != null ? totalIncomePersonal : 0L);
        listTotalIncomeAndExpensePersonalAndExpenseFamilyInMonth.add(totalExpensePersonal != null ? totalExpensePersonal : 0L);
        listTotalIncomeAndExpensePersonalAndExpenseFamilyInMonth.add(totalExpenseFamily != null ? totalExpenseFamily : 0L);
        return ApiResponse.builder().status(200).message("Tổng thu nhập các nhân, chi tiêu cá nhân, chi tiêu gia đình trong tháng").data(listTotalIncomeAndExpensePersonalAndExpenseFamilyInMonth).build();
    }

    // lấy tồng thu nhập cá nhân của tất cả các tháng trong năm
    @Override
    public ApiResponse<Object> getTotalIncomeAndExpensePersonalAndExpenseFamilyInYear(Long idUser, Integer year)
    {
        List<Long> listTotalIncomePersonalInYear = new ArrayList<>();
        List<Long> listTotalExpensePersonalInYear = new ArrayList<>();
        List<Long> listTotalExpenseFamilyInYear = new ArrayList<>();

        for(int i = 1; i <= 12; i++)
        {
            Long totalIncomePersonal = transactionRepository.getTotalIncomePersonalInMonth(idUser, i, year);
            Long totalExpensePersonal = transactionRepository.getTotalExpensePersonalInMonth(idUser, i, year);
            Long totalExpenseFamily = transactionRepository.getTotalExpenseFamilyInMonth(idUser, i, year);

            listTotalIncomePersonalInYear.add(totalIncomePersonal != null ? totalIncomePersonal : 0L);
            listTotalExpensePersonalInYear.add(totalExpensePersonal != null ? totalExpensePersonal : 0L);
            listTotalExpenseFamilyInYear.add(totalExpenseFamily != null ? totalExpenseFamily : 0L);
        }
        return ApiResponse.builder().status(200).message("Tồng thu nhập cá nhân của tất cả các tháng trong năm").listTotalIncomePersonalInYear(listTotalIncomePersonalInYear).listTotalExpensePersonalInYear(listTotalExpensePersonalInYear).listTotalExpenseFamilyInYear(listTotalExpenseFamilyInYear).build();
    }


    //  lấy giao dịch thu nhập cá nhân trong 1 tháng trong 1 năm
    @Override
    public ApiResponse<Object> getAllTransactionIncomePersonalInMonth(Long idUser, Integer month, Integer year)
    {
        Iterable<Transactions> transactionsIncomeInMonth = transactionRepository.getAllIncomeTransactionPersonalInMonth(idUser, month, year);
        if(!transactionsIncomeInMonth.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chưa có giao dịch thu nhập cá nhân trong tháng").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch thu nhập cá nhân trong tháng").data(transactionsIncomeInMonth).build();
    }

    // lấy giao dịch chi tiêu cá nhân trong 1 tháng trong 1 năm
    @Override
    public ApiResponse<Object> getAllTransactionExpensePersonalInMonth(Long idUser, Integer month, Integer year)
    {
        Iterable<Transactions> transactionsExpenseInMonth = transactionRepository.getAllExpenseTransactionPersonalInMonth(idUser, month, year);
        if(!transactionsExpenseInMonth.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chưa có giao dịch chi tiêu cá nhân trong tháng").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch chi tiêu cá nhân trong tháng").data(transactionsExpenseInMonth).build();
    }

    // lấy tất cả giao dịch chi tiêu gia đình trong tháng
    @Override
    public ApiResponse<Object> getAllTransactionExpenseFamilyInMonth(Long idUser, Integer month, Integer year)
    {
        Iterable<Transactions> transactionsExpenseInMonth = transactionRepository.getAllExpenseTransactionFamilyInMonth(idUser, month, year);
        if(!transactionsExpenseInMonth.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chưa có giao dịch chi tiêu gia đình trong tháng").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch chi tiêu gia đình trong tháng").data(transactionsExpenseInMonth).build();
    }

    // lấy tất cả giao dịch trong 1 khoảng thời gian
    @Override
    public ApiResponse<Object> getAllByTimeRange(Long idUser, Date startDate, Date endDate)
    {
        Iterable<Transactions> listTransactionByTimeRange = transactionRepository.getAllTransactionByStartAndEndDate(idUser, startDate, endDate);
        if(!listTransactionByTimeRange.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chua có giao dịch trong khoảng thời gian").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch trong khoảng thời gian").data(listTransactionByTimeRange).build();
    }

    // lấy tất cả giao dịch trong 1 tháng trong 1 năm
    @Override
    public ApiResponse<Object> getAllTransactionInMonth(Long idUser, Integer month, Integer year)
    {
        Iterable<Transactions> listTransactionInMonth = transactionRepository.getAllTransactionInMonth(idUser, month, year);
        if(!listTransactionInMonth.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chưa có giao dịch thu nhập trong tháng").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch trong tháng").data(listTransactionInMonth).build();
    }

    @Override
    public ApiResponse<Object> getAllTransactionInYear(Long idUser, Integer year)
    {
        Iterable<Transactions> listTransactionInYear = transactionRepository.getAllTransactionInYear(idUser, year);
        if(!listTransactionInYear.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Chưa có giao dịch thu nhập trong năm").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch trong năm").data(listTransactionInYear).build();
    }

    @Override
    public ApiResponse<Object> getTotalByUserCategory(Long idUser, Long idUserCategory)
    {
        Long totalByUserCategory = transactionRepository.getTotalByIdUserAndIdUserCategory(idUser, idUserCategory);
        if(totalByUserCategory == null)
        {
            return ApiResponse.builder().status(200).message("Tổng tiền giao dịch theo danh mục chi tiêu").data(0L).build();
        }
        return ApiResponse.builder().status(200).message("Tổng tiền giao dịch theo danh mục chi tiêu").data(totalByUserCategory).build();
    }

    // tìm kiếm giao dịch theo tên
    @Override
    public ApiResponse<Object> findTransactionByNameAndFamily(Long idUser, String name)
    {
        Iterable<Transactions> listTransaction = transactionRepository.findByNameAndFamily(idUser, name);
        if(!listTransaction.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Không có giao dịch cần tìm kiếm").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch tìm kiếm").data(listTransaction).build();
    }

    @Override
    public ApiResponse<Object> findTransactionIncomeByNameAndPersonal(Long idUser, String name)
    {
        Iterable<Transactions> listTransaction = transactionRepository.findByNameTransactionIncomeAndPersonal(idUser, name);
        if(!listTransaction.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Không có giao dịch cần tìm kiếm").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch tìm kiếm").data(listTransaction).build();
    }

    @Override
    public ApiResponse<Object> findTransactionExpenseByNameAndPersonal(Long idUser, String name)
    {
        Iterable<Transactions> listTransaction = transactionRepository.findByNameTransactionExpenseAndPersonal(idUser, name);
        if(!listTransaction.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Không có giao dịch cần tìm kiếm").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch tìm kiếm").data(listTransaction).build();
    }

    // lấy top các giao dịch gần đây
    @Override
    public ApiResponse<Object> get10TransactionPersonalIncomeNearest(Long idUser)
    {
        List<Transactions> listTransaction = transactionRepository.find10TransactionPersonalIncomeNearest(idUser);
        int size = listTransaction.size();
        int limit = 10;
        if (size > limit) {
            // Chỉ lấy 10 phần tử đầu tiên
            listTransaction = listTransaction.subList(0, limit);
        }
        return ApiResponse.builder().status(200).message("10 thu nhập cá nhân gần nhất").data(listTransaction).build();
    }

    @Override
    public ApiResponse<Object> get10TransactionPersonalExpenseNearest(Long idUser)
    {
        List<Transactions> listTransaction = transactionRepository.find10TransactionPersonalExpenseNearest(idUser);
        int size = listTransaction.size();
        int limit = 10;
        if (size > limit) {
            // Chỉ lấy 10 phần tử đầu tiên
            listTransaction = listTransaction.subList(0, limit);
        }
        return ApiResponse.builder().status(200).message("10 chi tiêu cá nhân gần nhất").data(listTransaction).build();
    }

    @Override
    public ApiResponse<Object> get5TransactionFamilyExpenseNearest(Long idUser)
    {
        List<Transactions> listTransaction = transactionRepository.find5TransactionFamilyExpenseNearest(idUser);
        int size = listTransaction.size();
        int limit = 5;
        if (size > limit) {
            // Chỉ lấy 10 phần tử đầu tiên
            listTransaction = listTransaction.subList(0, limit);
        }
        return ApiResponse.builder().status(200).message("5 chi tiêu gia đình cá nhân gần nhất").data(listTransaction).build();
    }

    // lấy giao dịch thu nhập cá nhân theo khoảng thời gian
    @Override
    public ApiResponse<Object> getAllTransactionPersonalIncomeByTimeRange(Long idUser, Date startDate, Date endDate)
    {
        Iterable<Transactions> listTransaction = transactionRepository.findAllTransactionPersonalIncomeByTimeRange(idUser, startDate, endDate);
        if(!listTransaction.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Không có giao dịch").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch").data(listTransaction).build();
    }

    // lấy giao dịch chi tiêu cá nhân theo khoảng thời gian
    @Override
    public ApiResponse<Object> getAllTransactionPersonalExpenseByTimeRange(Long idUser, Date startDate, Date endDate)
    {
        Iterable<Transactions> listTransaction = transactionRepository.findAllTransactionPersonalExpenseByTimeRange(idUser, startDate, endDate);
        if(!listTransaction.iterator().hasNext())
        {
            return ApiResponse.builder().status(101).message("Không có giao dịch").build();
        }
        return ApiResponse.builder().status(200).message("Danh sách giao dịch").data(listTransaction).build();
    }
}
