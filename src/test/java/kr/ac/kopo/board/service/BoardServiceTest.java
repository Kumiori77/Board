package kr.ac.kopo.board.service;

import kr.ac.kopo.board.dto.BoardDTO;
import kr.ac.kopo.board.dto.PageRequestDTO;
import kr.ac.kopo.board.dto.PageResultDTO;
import kr.ac.kopo.board.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){

        // 새로운 글 추가
        BoardDTO dto = BoardDTO.builder()
                .title("Test")
                .content("Test...")
                .writerEmail("user_100@kopo.com")
                .build();

        Long bno = boardService.register(dto);
        System.out.println("____________________");
        System.out.println(bno);
    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        }

    }

    @Test
    public void testGet() {

        Long bno = 100L;

        BoardDTO dto = boardService.get(bno);

        System.out.println(dto);

    }

    @Test
    public void testRemove() {

        Long bno = 100L;

        boardService.removeWithReplies(bno);

    }

    @Test
    public void testModify() {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(1L)
                .title("제목 변경")
                .content("내용 변경")
                .build();

        boardService.modify(boardDTO);
    }

}
