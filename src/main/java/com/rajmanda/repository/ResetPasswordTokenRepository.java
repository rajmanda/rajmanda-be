package com.rajmanda.repository;

import com.rajmanda.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, String> {
}
