package com.tranhuutruong.finance.build.repositories;

import com.tranhuutruong.finance.build.entities.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

    @Query("SELECT uc FROM UserCategory uc WHERE uc.userInformation.id = :id")
    Iterable<UserCategory> getAllByUser(@Param("id") Long idUser);

    @Query("SELECT uc FROM UserCategory uc WHERE uc.userInformation.id = :idUser AND uc.category.id = :idCategory AND uc.name = :name")
    UserCategory findByUserAndCategoryTypeAndName(@Param("idUser") Long idUser,@Param("idCategory") Long idCategory, @Param("name") String name);

    @Query("SELECT uc FROM UserCategory uc WHERE uc.id = :id AND uc.userInformation.id = :idUser")
    UserCategory finByIdAndUser(@Param("id") Long id, @Param("idUser") Long idUser);

    @Query("SELECT CASE WHEN COUNT(uc) > 0 THEN true ELSE false END FROM UserCategory uc WHERE uc.category.id = :idCategory")
    boolean findByCategoryType(@Param("idCategory") Long idCategory);

    // lấy danh mục thu nhập
    @Query("SELECT uc FROM UserCategory uc WHERE uc.userInformation.id = :idUser AND uc.category.id = 1")
    Iterable<UserCategory> getAllUserCategoryIncome(@Param("idUser") Long idUser);

    // lấy danh mục chi tiêu
    @Query("SELECT uc FROM UserCategory uc WHERE uc.userInformation.id = :idUser AND uc.category.id = 2")
    Iterable<UserCategory> getAllUserCategoryExpense(@Param("idUser") Long idUser);

    @Query("FROM UserCategory uc WHERE uc.userInformation.id = :idUser AND uc.name LIKE %:name%")
    Iterable<UserCategory> findByName(@Param("idUser") Long idUser, @Param("name") String name);
}
