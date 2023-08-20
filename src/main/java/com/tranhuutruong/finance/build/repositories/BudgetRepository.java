package com.tranhuutruong.finance.build.repositories;

import com.tranhuutruong.finance.build.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("SELECT b FROM Budget b WHERE b.userCategory.userInformation.id = :idUser")
    Iterable<Budget> getAllByUser(@Param("idUser") Long idUser);

    @Query("SELECT b FROM Budget b WHERE b.id = :id AND b.userCategory.userInformation.id = :idUser")
    Budget findByIdAndUser(@Param("id") Long idBudget, @Param("idUser") Long idUser);

    @Query("SELECT b FROM Budget b WHERE b.userCategory.id = :idUserCategory AND b.fromDate = :fromdate AND b.toDate = :todate")
    Budget findByIdUserCategoryAndFromDateAndToDate(@Param("idUserCategory") Long idUserCategory, @Param("fromdate") Date fromdate, @Param("todate") Date todate);

    @Query("SELECT b FROM Budget b WHERE b.userCategory.id = :id")
    Budget findByIdUserCategory(@Param("id") Long idUserCategory);

    @Query("FROM Budget b WHERE b.userCategory.userInformation.id = :idUser AND b.userCategory.name LIKE %:name%")
    Iterable<Budget> findByName(@Param("idUser") Long idUser, @Param("name") String name);
}
