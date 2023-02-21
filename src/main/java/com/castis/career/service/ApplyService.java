package com.castis.career.service;

import com.castis.career.dto.MailAttachedDTO;
import com.castis.career.dto.MailDTO;
import com.castis.career.entity.Apply;
import com.castis.career.entity.Board;
import com.castis.career.repository.ApplyRepository;
import com.castis.career.repository.BoardRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.MimeHeaders;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplyService {

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Value("${file.dir}")
    private String fileDir;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;


    //지원서 저장
    public void applyWrite(Long jobId, Apply apply, MultipartFile file) throws IOException {
        Board board = boardRepository.findById(jobId).orElse(null);

        if (!file.getOriginalFilename().equals("")){
        // 파일 이름으로 쓸 uuid 생성
        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(fileDir, fileName);

            // 실제로 로컬에 uuid를 파일명으로 저장
            file.transferTo(saveFile);

            apply.setFilename(file.getOriginalFilename());
            // 파일을 불러올 때 사용할 파일 경로
            apply.setFilepath(fileDir + fileName);
        }
        apply.setBoard(board);

        applyRepository.save(apply);
    }

    //지원서 목록 불러오기
    public List<Apply> applyList(){ return applyRepository.findAll(); }

    //지원서 삭제
    public void applyDelete(Long id) { applyRepository.deleteById(id); }

    //특정 지원서 불러오기
    public Apply applyView(Long id) { return applyRepository.findById(id).get(); }

    //지원 접수 시 메일 전송
    public void sendAttachedEmail(Apply apply, MultipartFile file) throws MessagingException, IOException {

        MailAttachedDTO mail = new MailAttachedDTO();

        mail.setAddress("taknineball@castis.com");
        mail.setFileName(apply.getFilename());
        mail.setContent("캐스트이즈 채용공고에 지원자가 있습니다. ADMIN 게시판을 확인해주세요.");
        mail.setTitle("[CASTIS recruit] 지원자가 있습니다.");
        mail.setCcAddress("taknineball@castis.com");

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        //메일 제목 설정
        helper.setSubject(mail.getTitle());

        //참조자 설정
        helper.setCc(mail.getCcAddress());
        helper.setFrom(from);

        helper.setText(mail.getContent(), false);

        //첨부 파일 설정
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        helper.addAttachment(MimeUtility.encodeText(fileName, "UTF-8", "B"), new ByteArrayResource(IOUtils.toByteArray(file.getInputStream())));

        helper.setTo(mail.getAddress());
        emailSender.send(message);
    }

    public void sendEmail(Apply apply) throws MessagingException, IOException {

        MailDTO applyMail = new MailDTO();

        applyMail.setAddress(apply.getEmail());
        applyMail.setContent("캐스트이즈 채용공고에 지원해주셔서 감사합니다.");
        applyMail.setTitle("[CASTIS] 지원해주셔서 감사합니다.");

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        //메일 제목 설정
        helper.setSubject(applyMail.getTitle());
        helper.setFrom(from);
        helper.setText(applyMail.getContent(), true);

        helper.setTo(applyMail.getAddress());
        emailSender.send(message);
    }
}
