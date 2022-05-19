package com.gabrisal.api.board.service;

import com.gabrisal.api.board.dto.AddBoardIn;
import com.gabrisal.api.board.dto.SearchBoardIn;
import com.gabrisal.api.board.dto.SearchBoardOut;
import com.gabrisal.api.board.model.Board;
import com.gabrisal.api.board.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService service;

    @Autowired
    BoardRepository repository;

    @DisplayName("게시판_목록_조회")
    @Test
    void getBoardList() {
        // given
        Board board = createBoard();
        for (int i=0; i<5; i++) {
            repository.insertBoard(board);
        }

        // when
        final List<SearchBoardOut> boardList = service.getBoardList();

        // then
        Assertions.assertThat(boardList.size()).isEqualTo(5);
    }


    @DisplayName("게시판_단건_조회")
    @Test
    void getBoardById() {
        // given
        Board board = createBoard();
        repository.insertBoard(board);

        // when
        SearchBoardIn searchBoardIn = new SearchBoardIn();
        searchBoardIn.setBoardId(board.getBoardId());
        SearchBoardOut result = service.getBoardById(searchBoardIn);

        // then
        Assertions.assertThat(result.getBoardId()).isEqualTo(board.getBoardId());
    }

    @DisplayName("게시판_글_작성")
    @Test
    void saveBoard() {
        // given
        AddBoardIn board = new AddBoardIn();
        board.setBoardTitle("Mock으로 테스트하고 싶었지만");
        board.setBoardContent("아직 익숙지않아서...... 실패!");
        board.setRegUserId("gabrisalTest");

        // when
        int result =service.saveBoard(board);

        // then
        Assertions.assertThat(result).isEqualTo(1);
    }

    @DisplayName("게시판_글_수정")
    @Test
    void modifyBoard() {
        // given
        Board board = createBoard();
        repository.insertBoard(board);

        AddBoardIn modifyBoard = new AddBoardIn();
        modifyBoard.setBoardId(board.getBoardId());
        modifyBoard.setBoardTitle("테스트합니다");
        modifyBoard.setBoardContent("테스트내용");
        modifyBoard.setRegUserId("gabrisalTest");

        // when
        int result = service.modifyBoard(modifyBoard);

        // then
        Assertions.assertThat(result).isEqualTo(1);
    }

    @DisplayName("게시판_글_삭제")
    @Test
    void deleteBoard() {
        // given
        Board board = createBoard();
        repository.insertBoard(board);

        SearchBoardIn deleteBoard = new SearchBoardIn();
        deleteBoard.setBoardId(board.getBoardId());

        // when
        int result = service.deleteBoard(deleteBoard);

        // then
        Assertions.assertThat(result).isEqualTo(1);
    }

    private Board createBoard() {
        Board board = new Board();
        board.setBoardTitle("게시판 제목");
        board.setBoardContent("게시판 내용가리");
        board.setFrstRegUserId("gabrisal");
        board.setLastUpdUserId("gabrisal");
        return board;
    }

}
