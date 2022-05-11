package com.gabrisal.api.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService service;

    @Test
    void getBoardById() {
        Board board = service.getBoardById(1);
        System.out.println("board :" + board);
    }

}
