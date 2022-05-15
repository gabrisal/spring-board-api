package com.gabrisal.api.board.model;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor @Getter @Setter @ToString
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
