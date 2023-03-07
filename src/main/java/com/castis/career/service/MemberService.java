package com.castis.career.service;

import com.castis.career.entity.Board;
import com.castis.career.entity.Member;
import com.castis.career.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        if (member == null){
            throw new UsernameNotFoundException(username);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }

    //member 목록 불러오기
    public List<Member> MemberList() {
        return memberRepository.findAll();
    }

    //member 추가
    public void save(Member member){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole("ADMIN");

        memberRepository.save(member);
    }

    //member 삭제
    public void memberDelete(Long id) { memberRepository.deleteById(id); }

}
