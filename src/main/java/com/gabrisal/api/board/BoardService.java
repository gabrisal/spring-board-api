package com.gabrisal.api.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository repository;

    public Board getBoardById(int boardId) {
        return repository.selectBoardOne(boardId);
    }
}
