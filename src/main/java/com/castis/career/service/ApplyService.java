package com.castis.career.service;

import com.castis.career.entity.Apply;
import com.castis.career.entity.Board;
import com.castis.career.repository.ApplyRepository;
import com.castis.career.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApplyService {

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Value("${file.dir}")
    private String fileDir;

    //지원서 저장
    public void applyWrite(Integer jobId, Apply apply, MultipartFile file) throws IOException {
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
    public void applyDelete(Integer id) { applyRepository.deleteById(id); }

    //특정 지원서 불러오기
    public Apply applyView(Integer id) { return applyRepository.findById(id).get(); }
}
