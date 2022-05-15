package com.gabrisal.api.board.model;

import lombok.*;

import java.sql.Timestamp;

@Builder @Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private int boardId;
    private String boardTitle;
    private String boardContent;
    private boolean delYn;
    private String frstRegUserId;
    private String frstRegUserIpAddr;
    private Timestamp frstRegDttm;
    private String lastUpdUserId;
    private String lastUpdUserIpAddr;
    private Timestamp lastUpdDttm;

}
