package kr.ac.kopo.board.service;

import kr.ac.kopo.board.dto.ReplyDTO;
import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Reply;
import kr.ac.kopo.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override // 등록
    public Long register(ReplyDTO replyDTO) {

        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);

        return reply.getRno();
    }

    @Override // 목록
    public List<ReplyDTO> getReplyList(Long bno) {

        List<Reply> result = replyRepository
                .getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());

        return result.stream().map(reply -> entityToDTO(reply))
                .collect(Collectors.toList());
    }

    @Override // 수정
    public void modify(ReplyDTO replyDTO) {

        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);

    }

    @Override // 삭제
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
