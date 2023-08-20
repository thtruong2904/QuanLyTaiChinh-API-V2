package com.tranhuutruong.finance.build.repositories.User;

import com.tranhuutruong.finance.build.entities.users.Account;
import com.tranhuutruong.finance.build.entities.users.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInformation, Long> {

    @Query("SELECT u FROM UserInformation u WHERE u.accountModel=:account")
    UserInformation findUserInfoModelByAccountModel(@Param("account") Account accountModel);

    @Query("SELECT u FROM UserInformation u WHERE u.id=:id")
    Optional<UserInformation> findById(@Param("id") Long id);

    @Query("SELECT u FROM UserInformation u WHERE u.accountModel.roleModel.name = :roleName")
    Iterable<UserInformation> findAllByRoleName(@Param("roleName") String roleName);
}
