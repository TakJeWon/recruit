package com.castis.career.repository;

import com.castis.career.entity.MailInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailInfoRepository extends JpaRepository<MailInfo, String> {
}
