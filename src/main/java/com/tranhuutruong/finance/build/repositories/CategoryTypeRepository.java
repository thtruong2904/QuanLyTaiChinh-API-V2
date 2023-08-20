package com.tranhuutruong.finance.build.repositories;

import com.tranhuutruong.finance.build.entities.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Long> {
    @Query("SELECT c FROM CategoryType c WHERE c.name=:name")
    CategoryType findByName(@Param("name") String name);
}
