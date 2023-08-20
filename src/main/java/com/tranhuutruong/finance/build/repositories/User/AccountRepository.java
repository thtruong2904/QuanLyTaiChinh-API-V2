package com.tranhuutruong.finance.build.repositories.User;

import com.tranhuutruong.finance.build.entities.users.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT u FROM Account u WHERE u.username=:userName")
    Account findAccountByUsername(@Param("userName") String userName);

    @Query("SELECT u FROM Account u WHERE u.email=:email")
    Account findAccountByEmail(@Param("email") String email);

    boolean existsByEmail( String email);
}
