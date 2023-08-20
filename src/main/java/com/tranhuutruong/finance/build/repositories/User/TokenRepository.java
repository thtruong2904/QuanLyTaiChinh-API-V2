package com.tranhuutruong.finance.build.repositories.User;

import com.tranhuutruong.finance.build.entities.users.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t WHERE t.userInformation.id = :userId")
    Token findByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM Token t WHERE t.tokenId = :tokenId")
    Token findByTokenId(@Param("tokenId") String tokenId);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.tokenId = :tokenId")
    int deleteTokenByTokenId(@Param("tokenId") String tokenId);
}
