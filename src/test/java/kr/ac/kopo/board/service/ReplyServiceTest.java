package kr.ac.kopo.board.service;

import kr.ac.kopo.board.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testGetList() {

        Long bno = 81L;

        List<ReplyDTO> replyDTOList = replyService.getReplyList(bno);

        replyDTOList.forEach(r -> {
            System.out.println(r);
        });

    }
}
