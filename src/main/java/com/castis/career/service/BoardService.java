package com.castis.career.service;

import com.castis.career.entity.Board;
import com.castis.career.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //공고 저장
    public void write(Board board) {
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
}

