package kr.ac.kopo.board.controller;

import kr.ac.kopo.board.dto.ReplyDTO;
import kr.ac.kopo.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno) {

        log.info("bno : " + bno);

        return new ResponseEntity<>(replyService.getReplyList(bno), HttpStatus.OK);

    }
    
    // 댓글 등록 처리
    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
        log.info(replyDTO);
        // DB의 댓글 테이블에 새로운 댓글 저장
        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    // 댓글 삭제 처리
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
        log.info("DELETE rno : " + rno);

        replyService.remove(rno); // 댓글 삭제

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
