package com.rajmanda.repository;

import com.rajmanda.entity.OauthToken;
import com.rajmanda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthTokenRepository extends JpaRepository<OauthToken, String> {
    OauthToken findByRefreshToken(String refreshToken);

    void deleteByUser(User user);
//    @Transactional
//    @Modifying
//    @Query("DELETE FROM OauthToken t WHERE t.expirationTime < ?1")
//    void deleteExpiredTokens(LocalDateTime currentTime);
}
