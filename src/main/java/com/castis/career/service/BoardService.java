package com.castis.career.service;

import com.castis.career.entity.Board;
import com.castis.career.repository.BoardRepository;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
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

        if (!file.getOriginalFilename().equals("") && board.getFilename() == null){
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

    //공고 파일 변경 없이 저장
    public void noFileChanged(Board board) throws IOException {
        boardRepository.save(board);
    }

    //공고 목록 불러오기
    public List<Board> boardList() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "Id"));
    }

    //공고 삭제
    public void boardDelete(Long id){
        Board deleteBoard = boardRepository.findById(id).orElse(null);

        File file = new File(deleteBoard.getFilepath());
        if (file.exists()){
            file.delete();
        }
        boardRepository.deleteById(id);
    }

    public void boardFileDelete(Long id){
        Board deleteBoard = boardRepository.findById(id).orElse(null);

        if (deleteBoard.getFilepath() != null){
            File file = new File(deleteBoard.getFilepath());
            if (file.exists()){
                deleteBoard.setFilepath(null);
                deleteBoard.setFilename(null);
                boardRepository.save(deleteBoard);
                file.delete();
            }

        }
    }

    //특정 게시글 불러오기
    public Board boardView(Long id) { return boardRepository.findById(id).get(); }

}

