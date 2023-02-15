package com.castis.career.repository;

import com.castis.career.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //select * from member where memberEmail = ?
    //리턴타입 : Member
    //매개변수 : memberEmail(String)
    Member findByEmail(String email);
}
