package kr.ac.kopo.board.repository;

import jakarta.transaction.Transactional;
import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Member;
import org.apache.juli.logging.Log;
import org.hibernate.internal.log.SubSystemLogging;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.awt.datatransfer.SystemFlavorMap;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            // 외래키로 사용할 Member 객체 생성
            Member member = Member.builder()
                    .email("user_" + i + "@kopo.com")
                    .build();

            // 엔티티 객체 생성
            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content......" + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });

    }

    @Transactional
    @Test
    public void testRead1() {

        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());

    }

    @Test
    public void testReadWithWriter() {

        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("__________________");
        System.out.println(Arrays.toString(arr));

    }

    @Test
    public void testGetBoardWithReply() {

        List<Object[]> result = boardRepository.getBoardWithReply(81L);

       for (Object[] arr : result) {
           System.out.println(Arrays.toString(arr));
       }

    }

    @Test
    public void testWithReplyCount() {

        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;

            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3(){

        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) result;

        for (Object data : arr){
            System.out.println(data);
        }

    }
    @Test
    public void testSearch1() {
        boardRepository.search1();
    }

    @Test
    public void testSearchPage() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending()
                .and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);

        System.out.println("++++++++++++++++++++++++++++");
        System.out.println(result);
    }

}
