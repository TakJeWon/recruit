package com.castis.career.service;

import com.castis.career.entity.MailInfo;
import com.castis.career.repository.MailInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailInfoService {

    @Autowired
    private MailInfoRepository mailInfoRepository;

    //메일 보낼 정보 불러오기
    public List<MailInfo> mailInfoList(){
        return mailInfoRepository.findAll();
    }

    //특정 정보 불러오기
    public MailInfo mailInfo(String id){
        return mailInfoRepository.findById(id).get();
    }

    //메일 보낼 정보 저장
    public void write(MailInfo mailInfo) throws IOException {
        mailInfoRepository.save(mailInfo);
    }


}
