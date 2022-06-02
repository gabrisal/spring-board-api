package com.gabrisal.api.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Schema(description = "게시판 조회 응답")
@Getter
@Setter
@NoArgsConstructor
public class SearchBoardOut {

    @Schema(description = "게시글 ID", required = true, example = "1")
    private int boardId;

    @Schema(description = "게시글 제목", required = true, example = "제목")
    private String boardTitle;

    @Schema(description = "게시글 내용", required = true, example = "내용")
    private String boardContent;

    @Schema(description = "작성자", required = true, example = "gabrisal")
    private String writer;

    @Schema(description = "최초등록일시", required = true, example = "20220602")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp frstRegDttm;

    @Schema(description = "최종수정일시", required = true, example = "20220602")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp lastUpdDttm;

    @Schema(description = "태그목록", required = true, example = "#태그1#태그2#태그3")
    private String tags;

}
