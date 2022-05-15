package com.gabrisal.api.board.dto;

import com.gabrisal.api.board.model.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddBoardIn {

    private int boardId;
    private String boardTitle;
    private String boardContent;
    private String regUserId;
    private List<Tag> tagList;

}
