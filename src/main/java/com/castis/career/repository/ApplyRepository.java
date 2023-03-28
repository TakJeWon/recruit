package com.castis.career.repository;

import com.castis.career.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {

    List<Apply> findByBoardId(Long boardId);
}
