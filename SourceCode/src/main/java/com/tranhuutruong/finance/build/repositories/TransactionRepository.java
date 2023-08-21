package com.tranhuutruong.finance.build.repositories;

import com.tranhuutruong.finance.build.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Transactions t WHERE t.transactionType.id = :id")
    boolean existsByTransactionType(@Param("id") Long idTransactionType);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Transactions t WHERE t.userCategory.id = :id")
    boolean existsByUserCategory(@Param("id") Long idUserCategory);

    @Query("SELECT t FROM Transactions t WHERE t.id = :id AND t.userCategory.userInformation.id = :idUser")
    Transactions findByIdAndUser(@Param("id") Long idTransaction, @Param("idUser") Long idUser);

    @Query("SELECT t " +
        "FROM Transactions t " +
        "JOIN TransactionType tt ON t.transactionType.id = tt.id " +
        "JOIN UserCategory uc ON t.userCategory.id = uc.id " +
        "WHERE MONTH(t.date) = :month AND YEAR(t.date) = :year AND tt.id = 1 AND uc.userInformation.id = :userId")
    Iterable<Transactions> findTransactionsByTransactionTypeAndMonthAndUserIdFamily(@Param("month") Integer month, @Param("year") Integer year, @Param("userId") Long userId);

    @Query("SELECT t " +
            "FROM Transactions t " +
            "JOIN TransactionType tt ON t.transactionType.id = tt.id " +
            "JOIN UserCategory uc ON t.userCategory.id = uc.id " +
            "WHERE MONTH(t.date) = :month AND YEAR(t.date) = :year AND tt.id = 2 AND uc.userInformation.id = :userId")
    Iterable<Transactions> findTransactionsByTransactionTypeAndMonthAndUserIdPersonal(@Param("month") Integer month, @Param("year") Integer year, @Param("userId") Long userId);

    // lấy tổng tiền của các giao dịch thu nhập cá nhân trong 1 khoảng thời gian
    @Query("SELECT SUM(t.amount) " +
            "FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 2 " +
            "AND uc.category.id = 1 " +
            "AND t.date BETWEEN :startDate AND :endDate " +
            "AND YEAR(t.date) = :year")
    Long getTotalIncomeInStartDateAndEndDate(@Param("idUser") Long idUser, @Param("startDate")Date startDate, @Param("endDate") Date endDate, @Param("year") Integer year);

    // lấy tổng tiền của các giao dịch chi tiêu cá nhân trong 1 khoảng thời gian
    @Query("SELECT SUM(t.amount) " +
            "FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 2 " +
            "AND uc.category.id = 2 " +
            "AND t.date BETWEEN :startDate AND :endDate " +
            "AND YEAR(t.date) = :year")
    Long getTotalExpenseInStartDateAndEndDate(@Param("idUser") Long idUser, @Param("startDate")Date startDate, @Param("endDate") Date endDate, @Param("year") Integer year);

    @Query("SELECT SUM(t.amount) " +
            "FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 1 " +
            "AND uc.category.id = 2 " +
            "AND t.date BETWEEN :startDate AND :endDate " +
            "AND YEAR(t.date) = :year")
    Long getTotalExpenseFamilyInStartDateAndEndDate(@Param("idUser") Long idUser, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("year") Integer year);


    // lấy tổng thu nhập cá nhân trong 1 tháng trong 1 năm
    @Query("SELECT SUM(t.amount) FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 2 " +
            "AND uc.category.id = 1 " +
            "AND MONTH(t.date) = :month " +
            "AND YEAR(t.date) = :year")
    Long getTotalIncomePersonalInMonth(@Param("idUser") Long idUser, @Param("month") Integer month, @Param("year") Integer year);


    // tổng chi tiêu cá nhân trong 1 tháng trong 1 năm
    @Query("SELECT SUM(t.amount) FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 2 " +
            "AND uc.category.id = 2 " +
            "AND MONTH(t.date) = :month " +
            "AND YEAR(t.date) = :year")
    Long getTotalExpensePersonalInMonth(@Param("idUser") Long idUser, @Param("month") Integer month, @Param("year") Integer year);

    // tổng chi tiêu gia đình trong 1 tháng trong 1 năm
    @Query("SELECT SUM(t.amount) FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 1 " +
            "AND uc.category.id = 2 " +
            "AND MONTH(t.date) = :month " +
            "AND YEAR(t.date) = :year ")
    Long getTotalExpenseFamilyInMonth(@Param("idUser") Long idUser, @Param("month") Integer month, @Param("year") Integer year);

    // tất cả giao dịch thu nhập trong 1 tháng
    @Query("SELECT t FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 2 " +
            "AND uc.category.id = 1 " +
            "AND MONTH(t.date) = :month " +
            "AND YEAR(t.date) = :year " +
            "ORDER BY t.date DESC")
    Iterable<Transactions> getAllIncomeTransactionPersonalInMonth(@Param("idUser") Long idUser, @Param("month") Integer month, @Param("year") Integer year);

    // tất cả giao dịch chi tiêu cá nhân trong 1 tháng
    @Query("SELECT t FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 2 " +
            "AND uc.category.id = 2 " +
            "AND MONTH(t.date) = :month " +
            "AND YEAR(t.date) = :year " +
            "ORDER BY t.date DESC")
    Iterable<Transactions> getAllExpenseTransactionPersonalInMonth(@Param("idUser") Long idUser, @Param("month") Integer month, @Param("year") Integer year);

    // lấy tất cả giao dịch gia đình trong 1 tháng trong 1 năm
    @Query("SELECT t FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 1 " +
            "AND uc.category.id = 2 " +
            "AND MONTH(t.date) = :month " +
            "AND YEAR(t.date) = :year " +
            "ORDER BY t.date DESC")
    Iterable<Transactions> getAllExpenseTransactionFamilyInMonth(@Param("idUser") Long idUser, @Param("month") Integer month, @Param("year") Integer year);

    // lấy tất cả giao dịch của người dùng trong 1 khoảng thời gian
    @Query("SELECT t FROM Transactions t " +
            "WHERE t.userCategory.userInformation.id = :idUser " +
            "AND t.date BETWEEN :startDate AND :endDate")
    Iterable<Transactions> getAllTransactionByStartAndEndDate(@Param("idUser") Long idUser, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // lấy tất cả giao dịch trong 1 tháng trong 1 năm
    @Query("SELECT t FROM Transactions t " +
            "WHERE t.userCategory.userInformation.id = :idUser " +
            "AND MONTH(t.date) = :month AND YEAR(t.date) = :year")
    Iterable<Transactions> getAllTransactionInMonth(@Param("idUser") Long idUser, @Param("month") Integer month, @Param("year") Integer year);

    // lấy tất cả giao dịch trong 1 năm
    @Query("SELECT t FROM Transactions t " +
            "WHERE t.userCategory.userInformation.id = :idUser " +
            "AND YEAR(t.date) = :year")
    Iterable<Transactions> getAllTransactionInYear(@Param("idUser") Long idUser, @Param("year") Integer year);

    // lấy tổng tiền của các giao dịch theo danh mục chi tiêu
    @Query("SELECT SUM(t.amount) FROM Transactions t " +
            "WHERE t.userCategory.userInformation.id = :idUser " +
            "AND t.userCategory.id = :idUserCategory")
    Long getTotalByIdUserAndIdUserCategory(@Param("idUser") Long idUser, @Param("idUserCategory") Long idUserCategory);

    @Query("FROM Transactions t WHERE t.userCategory.userInformation.id = :idUser AND t.name LIKE %:name% AND t.transactionType.id = 1")
    Iterable<Transactions> findByNameAndFamily(@Param("idUser") Long idUser, @Param("name") String name);

    @Query("FROM Transactions t WHERE t.userCategory.userInformation.id = :idUser AND t.name LIKE %:name% AND t.transactionType.id = 2 AND t.userCategory.category.id = 1")
    Iterable<Transactions> findByNameTransactionIncomeAndPersonal(@Param("idUser") Long idUser, @Param("name") String name);

    @Query("FROM Transactions t WHERE t.userCategory.userInformation.id = :idUser AND t.name LIKE %:name% AND t.transactionType.id = 2 AND t.userCategory.category.id = 2")
    Iterable<Transactions> findByNameTransactionExpenseAndPersonal(@Param("idUser") Long idUser, @Param("name") String name);

    // lấy ra 10 giao dịch thu nhập cá nhân gần nhất
    @Query("SELECT t FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 2 " +
            "AND uc.category.id = 1 " +
            "ORDER BY t.date DESC")
    List<Transactions> find10TransactionPersonalIncomeNearest(@Param("idUser") Long idUser);

    // lấy ra 10 giao dịch chi tiêu cá nhân gần nhất
    @Query("SELECT t FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 2 " +
            "AND uc.category.id = 2 " +
            "ORDER BY t.date DESC")
    List<Transactions> find10TransactionPersonalExpenseNearest(@Param("idUser") Long idUser);

    // lấy ra 5 giao dịch chi tiêu gia đình gần nhất
    @Query("SELECT t FROM Transactions t " +
            "JOIN t.userCategory uc " +
            "JOIN t.transactionType tt " +
            "JOIN uc.userInformation ui " +
            "WHERE ui.id = :idUser " +
            "AND tt.id = 1 " +
            "AND uc.category.id = 2 " +
            "ORDER BY t.date DESC")
    List<Transactions> find5TransactionFamilyExpenseNearest(@Param("idUser") Long idUser);

    // lấy ra giao dịch thu nhập cá nhân trong khoảng thời gian
    @Query("FROM Transactions t WHERE t.userCategory.userInformation.id = :idUser AND t.userCategory.category.id = 1 AND t.transactionType = 2 AND t.date BETWEEN :startDate AND :endDate")
    Iterable<Transactions> findAllTransactionPersonalIncomeByTimeRange(@Param("idUser") Long idUser, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // lấy ra giao dịch chi tiêu cá nhân trong khoảng thời gian
    @Query("FROM Transactions t WHERE t.userCategory.userInformation.id = :idUser AND t.userCategory.category.id = 2 AND t.transactionType = 2 AND t.date BETWEEN :startDate AND :endDate")
    Iterable<Transactions> findAllTransactionPersonalExpenseByTimeRange(@Param("idUser") Long idUser, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
