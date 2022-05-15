package com.gabrisal.api.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class SearchBoardOut {

    private int boardId;
    private String boardTitle;
    private String boardContent;
    private String writer;
    private Timestamp frstRegDttm;
    private String tags;

}
