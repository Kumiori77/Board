package kr.ac.kopo.board.repository;

import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {

        IntStream.rangeClosed(1, 300).forEach(i -> {
//            long bno = (long)(Math.random() * 100) + 1;
            long bno = new Random().nextInt(100) + 1;

            // 외래키로 참조할 엔티티 객체
            Board board = Board.builder()
                    .bno(bno).build();

            // 엔티티 객체 생성
            Reply reply = Reply.builder()
                    .text("Reply......" + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });

    }

    @Test
    public void testListByBoard() {

        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(
                Board.builder().bno(81L).build()
        );

        replyList.forEach(r -> {
            System.out.println(r);
        });

    }
}
