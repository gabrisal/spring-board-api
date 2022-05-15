package com.gabrisal.api.board.model;

import lombok.*;

import java.sql.Timestamp;

@Builder @Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    private int tagId;
    private String tagName;
    private boolean delYn;
    private String frstRegUserId;
    private String frstRegUserIpAddr;
    private Timestamp frstRegDttm;
    private String lastUpdUserId;
    private String lastUpdUserIpAddr;
    private Timestamp lastUpdDttm;

}
