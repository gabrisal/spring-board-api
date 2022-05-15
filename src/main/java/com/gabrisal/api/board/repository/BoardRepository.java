package com.gabrisal.api.board.repository;

import com.gabrisal.api.board.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardRepository {

    Board selectBoardOne(int boardId);

    List<Board> selectBoardList();

    int insertBoard(Board board);

    int updateBoard(Board board);

    int deleteBoardById(int boardId);

}
