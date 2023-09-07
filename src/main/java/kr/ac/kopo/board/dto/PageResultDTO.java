package kr.ac.kopo.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

    // DTO 리스트
    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private  int page;

    // 목록 사이즈
    private int size;

    // 시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    // 이전, 다음 링크 표시 여부
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {

        // DTO 리스트 생성
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        // 총 페이지수 계산 (전체 페이지 번호 개수 = 테이블의 엔티티 개수)
        totalPage = result.getTotalPages();

        // 화면에 표시되는 페이지 리스트 생성
        makePageList(result.getPageable());

    }

    // 페이지 리스트 생성 메소드
    private void makePageList(Pageable pageable) {
        // Page객체로 부터 현재 페이지 번호와 페이지 사이즈 받아옴
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        // 임시 마지막 체이지
        int tmpEnd = (int)(Math.ceil(page / 10.0)) * 10;

        this.start = tmpEnd - 9; // 시작 페이지
        this.prev = start > 1; // 이전 링크 표시 여부
        this.end = totalPage > tmpEnd ? tmpEnd : totalPage; // 진짜 마지막 페이지
        this.next = totalPage > tmpEnd; // 다음 링크 표시 여부

        // 화면에 표시되는 페이지 번호들을 List<>로 생성
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }

}