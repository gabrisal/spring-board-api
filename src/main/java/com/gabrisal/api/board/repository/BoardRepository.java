package com.gabrisal.api.board.repository;

import com.gabrisal.api.board.dto.SearchBoardIn;
import com.gabrisal.api.board.dto.SearchBoardOut;
import com.gabrisal.api.board.model.Board;
import com.gabrisal.api.board.model.BoardTag;
import com.gabrisal.api.board.model.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardRepository {

    SearchBoardOut selectBoardOne(SearchBoardIn in);

    List<SearchBoardOut> selectBoardList();

    int insertBoard(Board board);

    int updateBoard(Board board);

    int deleteBoardById(int boardId);

    int insertTag(Tag tag);

    int insertBoardTagRel(BoardTag boardTag);

}
