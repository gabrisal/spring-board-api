package com.gabrisal.api.board;

import com.gabrisal.api.board.model.Board;
import com.gabrisal.api.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService service;

    @Test
    void getBoardById() {
        Board board = service.getBoardById(2);
        System.out.println("============= board :" + board);
    }

    @Test
    void getBoardList() {
        List<Board> boardList = service.getBoardList();
        System.out.println("============= boardList :" + boardList);
    }

}
