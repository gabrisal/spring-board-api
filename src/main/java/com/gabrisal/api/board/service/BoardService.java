package com.gabrisal.api.board.service;

import com.gabrisal.api.board.dto.AddBoardIn;
import com.gabrisal.api.board.dto.SearchBoardIn;
import com.gabrisal.api.board.dto.SearchBoardOut;
import com.gabrisal.api.board.model.Board;
import com.gabrisal.api.board.model.BoardTag;
import com.gabrisal.api.board.model.Tag;
import com.gabrisal.api.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository repository;

    @Transactional(readOnly = true)
    public SearchBoardOut getBoardById(SearchBoardIn in) {
        return repository.selectBoardOne(in);
    }

    @Transactional(readOnly = true)
    public List<SearchBoardOut> getBoardList() {
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

    public int insertTag(Tag tag) {
        return repository.insertTag(tag);
    }

    public int insertBoardTagRel(BoardTag boardTag) {
        return repository.insertBoardTagRel(boardTag);
    }

    public int saveBoard(AddBoardIn in) {
        Board board = Board.builder()
                    .boardTitle(in.getBoardTitle())
                    .boardContent(in.getBoardContent())
                    .frstRegUserId(in.getRegUserId())
                    .lastUpdUserId(in.getRegUserId())
                    .build();
        int result = insertBoard(board);

        for (Tag tag : in.getTagList()) {
            // XXX: 태그에 시스템 컬럼이 필요할까?
            tag.setFrstRegUserId(in.getRegUserId());
            tag.setLastUpdUserId(in.getRegUserId());
            insertTag(tag);

            BoardTag boardTag = BoardTag.builder()
                    .boardId(board.getBoardId())
                    .tagId(tag.getTagId())
                    .frstRegUserId(in.getRegUserId())
                    .lastUpdUserId(in.getRegUserId())
                    .build();
            insertBoardTagRel(boardTag);
        }

        return result;
    }
}
