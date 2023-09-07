package kr.ac.kopo.board.repository;

import kr.ac.kopo.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b, r from Board b LEFT JOIN Reply r ON b = r.board where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    // 목록 화면에 사용되는 데이터행의 결과 + 댓글 개수

    @Query(value = "SELECT b, w, count(r) " +
    "FROM Board b LEFT JOIN b.writer w LEFT JOIN Reply r ON r.board = b" +
    " GROUP BY b",
    countQuery = "SELECT COUNT(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    // 조회 화면에 사용되는 데이터의 결과 + 댓글 개수
    @Query("SELECT b, w, count(r) " +
    "FROM Board b LEFT JOIN b.writer w " +
    "LEFT OUTER JOIN Reply r ON r.board = b " +
    "WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);

}
