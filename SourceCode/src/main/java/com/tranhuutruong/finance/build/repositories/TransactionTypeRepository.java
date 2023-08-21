package com.tranhuutruong.finance.build.repositories;

import com.tranhuutruong.finance.build.entities.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

    @Query("SELECT t FROM TransactionType t WHERE t.name=:name")
    TransactionType findByName(@Param("name") String name);

}
