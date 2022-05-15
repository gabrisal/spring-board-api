package com.gabrisal.api.board;

import com.gabrisal.api.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService service;

    @DisplayName("게시판_단건_조회")
    @Test
    void getBoardById() {
        // given
        // when
//        Board board = service.getBoardById(boardId);
//        System.out.println("============= board :" + board);

        // then
//        Assertions.assertThat(board).

    }

    @DisplayName("게시판_목록_조회")
    @Test
    void getBoardList() {
    }

}
