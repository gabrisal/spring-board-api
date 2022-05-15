package com.gabrisal.api.board.service;

import com.gabrisal.api.board.model.Board;
import com.gabrisal.api.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository repository;

    @Transactional(readOnly = true)
    public Board getBoardById(int boardId) {
        return repository.selectBoardOne(boardId);
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardList() {
        return repository.selectBoardList();
    }

    public int insertBoard(Board board) {
        return repository.insertBoard(board);
    }

    public int updateBoard(Board board) {
        return repository.updateBoard(board);
    }

    public int deleteBoardById(int boardId) {
        return repository.deleteBoardById(boardId);
    }
}
