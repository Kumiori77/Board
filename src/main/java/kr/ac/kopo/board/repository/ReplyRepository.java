package kr.ac.kopo.board.repository;

import kr.ac.kopo.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying
    @Query("DELETE FROM Reply r WHERE r.board.bno = :bno ")
    void deleteByBno(@Param("bno")Long bno);



}
