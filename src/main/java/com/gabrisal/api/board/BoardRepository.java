package com.gabrisal.api.board;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardRepository {

    Board selectBoardOne(int boardId);
    List<Board> selectBoardList();
    void insertBoard(Board board);

}
