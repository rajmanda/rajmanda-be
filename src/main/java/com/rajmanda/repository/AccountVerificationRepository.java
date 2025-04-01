package com.rajmanda.repository;

import com.rajmanda.entity.AccountVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountVerificationRepository extends JpaRepository<AccountVerification, String> {
}
