package com.gabrisal.api.board.service;

import com.gabrisal.api.board.dto.AddBoardIn;
import com.gabrisal.api.board.dto.SearchBoardIn;
import com.gabrisal.api.board.dto.SearchBoardOut;
import com.gabrisal.api.board.model.Board;
import com.gabrisal.api.board.model.BoardTag;
import com.gabrisal.api.board.model.Tag;
import com.gabrisal.api.board.repository.BoardRepository;
import com.gabrisal.api.common.util.MaskingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        // XXX : map 은 왜 안될까
        List<SearchBoardOut> boardList = repository.selectBoardList();
        boardList.forEach(v -> v.setWriter(MaskingUtil.getMaskedName(v.getWriter())));
        return boardList;
    }

    private int insertBoard(Board board) {
        return repository.insertBoard(board);
    }

    private int updateBoard(Board board) {
        return repository.updateBoard(board);
    }

    private int deleteBoardById(int boardId) {
        return repository.deleteBoardById(boardId);
    }

    @Transactional(readOnly = true)
    public Tag selectTagByName(Tag tag) {
        return repository.selectTagByName(tag);
    }

    private int insertTag(Tag tag) {
        return repository.insertTag(tag);
    }

    private int updateTag(Tag tag) {
        return repository.updateTag(tag);
    }

    private int insertBoardTagRel(BoardTag boardTag) {
        return repository.insertBoardTagRel(boardTag);
    }

    private int deleteBoardTagRelByBoardId(BoardTag boardTag) {
        return repository.deleteBoardTagRelByBoardId(boardTag);
    }

    public int saveBoard(AddBoardIn in) {
        Board board = Board.builder()
                    .boardTitle(in.getBoardTitle())
                    .boardContent(in.getBoardContent())
                    .frstRegUserId(in.getRegUserId())
                    .lastUpdUserId(in.getRegUserId())
                    .build();
        int result = insertBoard(board);
        saveHashTagList(in, board);
        return result;
    }

    public int modifyBoard(AddBoardIn in) {
        // TODO: 유효한 게시글이 아닐 경우 처리
        Board board = Board.builder()
                .boardId(in.getBoardId())
                .boardTitle(in.getBoardTitle())
                .boardContent(in.getBoardContent())
                .frstRegUserId(in.getRegUserId())
                .lastUpdUserId(in.getRegUserId())
                .build();
        int result = updateBoard(board);

        BoardTag boardTag = BoardTag.builder()
                .boardId(board.getBoardId())
                .build();
        deleteBoardTagRelByBoardId(boardTag);

        // XXX: 해시태그를 참조하는 게시글이 사라지면 해시태그도 지워야하지않을까?

        saveHashTagList(in, board);

        return result;
    }

    private void saveHashTagList(AddBoardIn in, Board board) {
        if (!CollectionUtils.isEmpty(in.getTagList())) {
            for (Tag tag : in.getTagList()) {
                Tag existTag = selectTagByName(tag);

                // XXX: 태그에 시스템 컬럼이 필요할까?
                tag.setFrstRegUserId(in.getRegUserId());
                tag.setLastUpdUserId(in.getRegUserId());
                if (existTag != null) {
                    tag.setTagId(existTag.getTagId());
                    updateTag(tag);
                } else {
                    insertTag(tag);
                }

                BoardTag boardTag = BoardTag.builder()
                        .boardId(board.getBoardId())
                        .tagId(tag.getTagId())
                        .frstRegUserId(in.getRegUserId())
                        .lastUpdUserId(in.getRegUserId())
                        .build();
                insertBoardTagRel(boardTag);
            }
        }
    }

    public int deleteBoard(SearchBoardIn board) {
        int result = deleteBoardById(board.getBoardId());
        BoardTag boardTag = BoardTag.builder()
                .boardId(board.getBoardId())
                .build();
        deleteBoardTagRelByBoardId(boardTag);
        return result;
    }
}
