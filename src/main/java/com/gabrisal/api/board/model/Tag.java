package com.gabrisal.api.board.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@ToString
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
