package com.castis.career.service;

import com.castis.career.entity.Board;
import com.castis.career.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Value("${file.dir}")
    private String fileDir;

    //공고 저장
    public void write(Board board, MultipartFile file) throws IOException {

        if (!file.getOriginalFilename().equals("")){
        // 파일 이름으로 쓸 uuid 생성
        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(fileDir, fileName);

        // 실제로 로컬에 uuid를 파일명으로 저장
        file.transferTo(saveFile);

        board.setFilename(file.getOriginalFilename());
        // 파일을 불러올 때 사용할 파일 경로
        board.setFilepath(fileDir + fileName);

        }

        boardRepository.save(board);
    }

    //공고 목록 불러오기
    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    //공고 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

    //특정 게시글 불러오기
    public Board boardView(Integer id) { return boardRepository.findById(id).get(); }

}

